package com.example.demo

import io.awspring.cloud.sqs.listener.MessageListenerContainerRegistry
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class Publisher {

  @Autowired
  lateinit var beanFactory: ListableBeanFactory

  val logger = LoggerFactory.getLogger(this::class.java)

  companion object {

    const val NUMBER_OF_MESSAGES_IN_BATCH_WORKING = 10
    const val NUMBER_OF_MESSAGES_IN_BATCH_ERROR = 11
  }

  @Autowired
  lateinit var sqsTemplate: SqsTemplate

  fun publish() {
    val items = listOf(Item("foo", "bar"), Item("foo"), Item(bar = "bar"))
    sqsTemplate.send("list-queue", items)
  }

  fun publishMany() {
    val container =
      beanFactory.getBeansOfType(MessageListenerContainerRegistry::class.java)["io.awspring.cloud.messaging.internalEndpointRegistryBeanName"]?.getContainerById("io.awspring.cloud.sqs.sqsListenerEndpointContainer#0")
        ?: return
    container.stop()

    val items = (0..1000).map { Item("f00-$it", "bar-$it") }
    val chunks = items
      .chunked(NUMBER_OF_MESSAGES_IN_BATCH_ERROR)
//      .chunked(NUMBER_OF_MESSAGES_IN_BATCH_WORKING)
    chunks.forEachIndexed { index, chunkedItems ->
      val messages = chunkedItems.map { MessageBuilder.withPayload(it).build() }
      logger.info("publishing chunk $index/${chunks.size}")
      sqsTemplate.sendMany("many-queue", messages)
    }
    logger.info("restarting container...")
    container.start()
    logger.info("Container restarted")
  }

  fun publishSingle() {
    sqsTemplate.send("single-queue", Item("foo", "bar"))
  }
}
