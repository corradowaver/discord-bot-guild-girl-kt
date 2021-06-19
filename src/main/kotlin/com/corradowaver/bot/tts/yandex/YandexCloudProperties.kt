package com.corradowaver.bot.tts.yandex

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "yandex.cloud")
data class YandexCloudProperties(
  var token: String,
  var folder: String,
)
