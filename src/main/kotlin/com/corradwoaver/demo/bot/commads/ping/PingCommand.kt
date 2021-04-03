package com.corradwoaver.demo.bot.commads.ping

import com.corradwoaver.demo.bot.commads.Command
import com.corradwoaver.demo.bot.message.MyMessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color.*

@Component
class PingCommand : Command {
    override val caller: String = "ping"
    override var event: GuildMessageReceivedEvent? = null

    override fun invoke(args: List<String>) {
        val messageTime = event!!.message.timeCreated.toInstant().toEpochMilli()
        val now = System.currentTimeMillis()
        val latency = now - messageTime
        event!!.channel.sendMessage(getMessage(latency)).queue()
    }

    private fun getMessage(latency: Long): MessageEmbed {
        val color = when (latency) {
            in 0..35 -> GREEN
            in 36..60 -> YELLOW
            in 61..90 -> ORANGE
            else -> RED
        }
        return MyMessageBuilder()
            .setTitle("$latency ms")
            .setDescription("Pong-pong")
            .setColor(color)
            .setFooter("made by corradowaver")
            .build()
    }

}
