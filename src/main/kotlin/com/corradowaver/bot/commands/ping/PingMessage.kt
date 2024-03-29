package com.corradowaver.bot.commands.ping

import com.corradowaver.bot.commands.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color.GREEN
import java.awt.Color.ORANGE
import java.awt.Color.RED
import java.awt.Color.YELLOW

@Component
final object PingMessage : MessageBuilder() {

  private var latency = 0L
  fun latency(latency: Long) = apply { PingMessage.latency = latency }

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    val color = when (latency) {
      in 0..90 -> GREEN
      in 91..120 -> YELLOW
      in 121..180 -> ORANGE
      else -> RED
    }
    return super
      .setTitle("$latency ms")
      .setDescription("Pong-pong")
      .setColor(color)
      .setFooter("made by corradowaver")
      .build()
  }
}
