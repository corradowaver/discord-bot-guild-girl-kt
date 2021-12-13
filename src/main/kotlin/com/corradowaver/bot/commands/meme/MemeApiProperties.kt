package com.corradowaver.bot.commands.meme

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "meme")
data class MemeApiProperties(
        var token: String = "",
)