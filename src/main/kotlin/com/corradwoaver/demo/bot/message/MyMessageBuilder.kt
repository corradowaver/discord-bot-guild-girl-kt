package com.corradwoaver.demo.bot.message

import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

class MyMessageBuilder : EmbedBuilder() {
    private val defaultTitlePrefix = "\uD83C\uDF08"

    init {
        super.setTitle("Default title")
            .setDescription("Default description")
            .setColor(Color.GREEN)
    }

    override fun setTitle(title: String?): EmbedBuilder {
        return super.setTitle("$defaultTitlePrefix $title")
    }

    fun setCustomPrefixedTitle(prefix: String, title: String): EmbedBuilder {
        return super.setTitle("$prefix $title")
    }
}
