package com.corradowaver.bot.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "aws")
data class AwsProperties(
  var accessKeyId: String,
  var secretKeyId: String,
)
