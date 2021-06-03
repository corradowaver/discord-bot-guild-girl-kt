package com.corradowaver.bot.commands.bitcoin

import com.corradowaver.bot.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color

@Component
final object BitcoinMessage : MessageBuilder() {

  private var rates = listOf<Pair<Currencies, String>>()
  fun rates(rates: List<Pair<Currencies, String>>) = apply { BitcoinMessage.rates = rates }


  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    val description: String = rates.joinToString(separator = "\n") {
      "${it.currency.sign}: _${it.price}_"
    }
    return super
      .setTitle("Bitcoin Rates")
      .setDescription(description)
      .setColor(Color.GREEN)
      .build()
  }
}

private val <A, B> Pair<A, B>.currency: A
  get() = first

private val <A, B> Pair<A, B>.price: B
  get() = second
