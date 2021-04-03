package com.corradwoaver.demo.bot.controller

import com.corradwoaver.demo.bot.Runner
import com.corradwoaver.demo.bot.commads.Command
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BotController(private val runner: Runner, private val commands: Map<String, Command>) {

    @GetMapping("/start")
    fun start(): Int {
        return 1
    }
}
