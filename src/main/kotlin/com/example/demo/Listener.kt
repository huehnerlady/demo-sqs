package com.example.demo

import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class Listener {

  val logger = LoggerFactory.getLogger(this::class.java)

  @SqsListener("list-queue")
  fun processCacheRequestList(items: List<Item?>?) {
    logger.info("items: $items")
    return
  }

  @SqsListener("many-queue", maxMessagesPerPoll = "50", maxConcurrentMessages = "50", pollTimeoutSeconds = "10")
  fun processCacheRequestMany(items: List<Item?>) {
    logger.info("received ${items.size} items")
    return
  }

  @SqsListener("single-queue")
  fun processCacheRequestSingle(item: Item?) {
    logger.info("item: $item")
    return
  }
}
