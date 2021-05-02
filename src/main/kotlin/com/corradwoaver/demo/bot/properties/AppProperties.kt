package com.corradwoaver.demo.bot.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "bot")
data class AppProperties(
  var token: String,
  var activity: String = "Tik-Tok",
  var prefix: String = "!"
)
