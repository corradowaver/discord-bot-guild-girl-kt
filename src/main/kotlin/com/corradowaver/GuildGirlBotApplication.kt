package com.corradowaver

import com.corradowaver.bot.properties.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class GuildGirlBotApplication

fun main(args: Array<String>) {
  runApplication<GuildGirlBotApplication>(*args)
}
