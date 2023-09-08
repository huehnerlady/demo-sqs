package com.example.demo

import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Publisher {

  @Autowired
  lateinit var sqsTemplate: SqsTemplate

  fun publish() {
    val items = listOf(Item("foo", "bar"), Item("foo"), Item(bar = "bar"))
    sqsTemplate.send("list-queue", items)
  }

  fun publishSingle() {
    sqsTemplate.send("single-queue", Item("foo", "bar"))
  }
}
