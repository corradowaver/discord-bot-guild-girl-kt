package com.corradowaver.kreed.bot.controller

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class BotController(val environment: Environment) {

  @GetMapping("/ping")
  fun start(): String {
    return "Server is running on ${environment.getProperty("local.server.address")}:${environment.getProperty("local.server.port")}"
  }
}
