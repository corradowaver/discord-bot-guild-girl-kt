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

  fun sendTimeHasNotCome(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createTimeHasNotComeMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setTitle("Ваш заказ принят!")
      .setDescription("Придумываю шутеечку.")
      .setColor(Color.GREEN)
      .build()
  }

  private fun createTimeHasNotComeMessage(): MessageEmbed {
    return super
      .setTitle("Падажжи")
      .setDescription("5 секунд можно подождать, не?")
      .setColor(Color.RED)
      .build()
  }
}
