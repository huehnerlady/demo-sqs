package com.example.demo

import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class Listener {

  val logger = LoggerFactory.getLogger(this::class.java)

  @SqsListener("list-queue")
  fun processCacheRequest(items: List<Item?>?) {
    logger.info("items: $items")
    return
  }

  @SqsListener("single-queue")
  fun processCacheRequest(item: Item?) {
    logger.info("item: $item")
    return
  }
}
