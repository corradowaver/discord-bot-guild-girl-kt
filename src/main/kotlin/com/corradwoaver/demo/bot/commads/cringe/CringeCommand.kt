package com.corradwoaver.demo.bot.commads.cringe

import com.corradwoaver.demo.bot.commads.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class CringeCommand(val messageBuilder: CringeMessage) : Command {
  override val caller: String = "cringe"

  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    messageBuilder.send(event)
  }
}
