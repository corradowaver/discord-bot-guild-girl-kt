package com.corradowaver.bot.commads.help

import com.corradowaver.bot.commads.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class HelpCommand(val commands: List<Command>, val messageBuilder: HelpMessage) : Command {
  override val caller: String = "help"
  override val description: String = "Help center"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    messageBuilder.commands(commands).send(event)
  }
}
