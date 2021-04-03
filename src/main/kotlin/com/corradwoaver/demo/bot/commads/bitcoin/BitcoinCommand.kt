package com.corradwoaver.demo.bot.commads.bitcoin

import com.corradwoaver.demo.bot.commads.Command
import com.corradwoaver.demo.bot.message.MyMessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color

@Component
class BitcoinCommand : Command {
    override val caller: String = "btc"
    override var event: GuildMessageReceivedEvent? = null

    override fun invoke(args: List<String>) {
        val responseJson = khttp.get("https://blockchain.info/ticker").jsonObject
        val rates = Currencies.values().map {
            it to responseJson.getJSONObject(it.name).get("last").toString()
        }
        event!!.channel.sendMessage(getMessage(rates)).queue()
    }

    private fun getMessage(rates: List<Pair<Currencies, String>>): MessageEmbed {
        val description: String = rates.joinToString(separator = "\n") {
            it.first.sign + " : " + it.second
        }
        return MyMessageBuilder()
            .setTitle("Bitcoin Rates")
            .setDescription(description)
            .setColor(Color.GREEN)
            .build()
    }
}
