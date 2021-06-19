package com.corradowaver.bot.commands.joke

import com.corradowaver.bot.commands.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color

@Component
final object JokeMessage : MessageBuilder() {

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setTitle("Joke:")
      .setDescription("WIP")
      .setColor(Color.GREEN)
      .setFooter("made by corradowaver")
      .build()
  }
}
