package com.corradwoaver.demo

import com.corradwoaver.demo.bot.properties.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class GuildGirlBotApplication

fun main(args: Array<String>) {
    runApplication<GuildGirlBotApplication>(*args)
    //khttp.get("http://localhost:8080/start")
}
