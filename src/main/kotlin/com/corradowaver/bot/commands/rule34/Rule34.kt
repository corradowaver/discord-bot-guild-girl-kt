package com.corradowaver.bot.commands.rule34


import com.corradowaver.bot.commands.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class Rule34Command(val messageBuilder: Rule34Message) : Command {
  override val caller: String = ""
  override val description = ""
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    messageBuilder.send(event)
  }
}
