package com.corradowaver

import com.corradowaver.bot.properties.AppProperties
import com.corradowaver.bot.tts.YandexCloudProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(AppProperties::class, YandexCloudProperties::class)
class GuildGirlBotApplication

fun main(args: Array<String>) {
  runApplication<GuildGirlBotApplication>(*args)
}
