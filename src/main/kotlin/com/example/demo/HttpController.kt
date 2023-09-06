package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.RestController

@RestController
class HttpController {

  val logger = LoggerFactory.getLogger(this::class.java)

  @Autowired
  lateinit var publisher: Publisher

  @RequestMapping(value = ["/send-single"], method = [POST])
  fun publishSingle() {
    logger.info("publishing single message")
    publisher.publishSingle()
  }

  @RequestMapping(value = ["/send-list"], method = [POST])
  fun publishList() {
    logger.info("publishing list of messages")
    publisher.publish()
  }
}
