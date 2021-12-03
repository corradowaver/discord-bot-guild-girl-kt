package com.corradowaver.bot.commands.prefix

import com.corradowaver.bot.commands.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color.GREEN
import java.awt.Color.RED

@Component
final object PrefixMessage : MessageBuilder() {
  private var prefix = PrefixCommand.prefix
  fun prefix(prefix: String) = apply { this.prefix = prefix }

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  fun sendPrefixChanged(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createPrefixChangedMessage()).queue()
  }

  fun sendErrorMessage(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createErrorMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setTitle("Current prefix is \"$prefix\"")
      .setDescription("You can change it by entering ${prefix}prefix <new_prefix>")
      .setColor(GREEN)
      .build()
  }

  fun createPrefixChangedMessage(): MessageEmbed {
    return super
      .setTitle("Prefix changed to \"$prefix\"")
      .setDescription("You can change it by entering ${prefix}prefix <new_prefix>")
      .setColor(GREEN)
      .build()
  }

  fun createErrorMessage(): MessageEmbed {
    return super
      .setTitle("You cannot specify prefix containing spaces")
      .setDescription("You can change prefix by entering ${prefix}prefix <new_prefix>")
      .setColor(RED)
      .build()
  }
}
