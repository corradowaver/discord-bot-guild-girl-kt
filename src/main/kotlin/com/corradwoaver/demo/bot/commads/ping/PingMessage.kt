package com.corradwoaver.demo.bot.commads.ping

import com.corradwoaver.demo.bot.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color

@Component
final object PingMessage : MessageBuilder() {

  private var latency = 0L
  fun latency(latency: Long) = apply { this.latency = latency }

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    val color = when (latency) {
      in 0..35 -> Color.GREEN
      in 36..60 -> Color.YELLOW
      in 61..90 -> Color.ORANGE
      else -> Color.RED
    }
    return super
      .setTitle("$latency ms")
      .setDescription("Pong-pong")
      .setColor(color)
      .setFooter("made by corradowaver")
      .build()
  }
}
