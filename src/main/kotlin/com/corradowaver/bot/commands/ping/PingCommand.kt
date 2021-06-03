package com.corradowaver.bot.commands.ping

import com.corradowaver.bot.commands.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class PingCommand(val messageBuilder: PingMessage) : Command {
  override val caller: String = "ping"
  override val description = "Ping-pong"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    val messageTime = event.message.timeCreated.toInstant().toEpochMilli()
    val now = System.currentTimeMillis()
    val latency = now - messageTime
    messageBuilder.latency(latency).send(event)
  }
}
