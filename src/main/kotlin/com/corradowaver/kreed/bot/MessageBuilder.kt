package com.corradowaver.kreed.bot

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color

abstract class MessageBuilder : EmbedBuilder() {
  private val defaultTitlePrefix = "\uD83C\uDF08"

  init {
    super.setTitle("Default title")
      .setDescription("Default description")
      .setColor(Color.GREEN)
  }

  abstract fun send(event: GuildMessageReceivedEvent)
  abstract fun createMessage(): MessageEmbed

  override fun setTitle(title: String?): EmbedBuilder {
    return super.setTitle("$defaultTitlePrefix $title")
  }

  fun setCustomPrefixedTitle(prefix: String, title: String): EmbedBuilder {
    return super.setTitle("$prefix $title")
  }
}
