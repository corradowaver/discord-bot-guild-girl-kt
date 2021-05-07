package com.corradowaver.bot.commads.help

import com.corradowaver.bot.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color.CYAN

@Component
final object HelpMessage : MessageBuilder() {

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setTitle("I'm Guild Girl, here are some commands")
      .setDescription("TODO iterate over services")
      .setColor(CYAN)
      .setFooter("made by corradowaver")
      .build()
  }
}
