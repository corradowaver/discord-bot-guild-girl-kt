package com.corradowaver.bot.commads.bitcoin

import com.corradowaver.bot.commads.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class BitcoinCommand(val messageBuilder: BitcoinMessage) : Command {
  override val caller: String = "btc"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    val responseJson = khttp.get("https://blockchain.info/ticker").jsonObject
    val rates = Currencies.values().map {
      it to responseJson.getJSONObject(it.name).get("last").toString()
    }
    messageBuilder.rates(rates).send(event)
  }
}
