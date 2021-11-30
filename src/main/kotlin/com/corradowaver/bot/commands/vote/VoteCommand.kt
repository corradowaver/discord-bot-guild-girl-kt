package com.corradowaver.bot.commands.vote

import com.corradowaver.bot.commands.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class VoteCommand(val messageBuilder: VoteMessage) : Command {
  override val caller: String = "vote"
  override val description = "ГОЛОСОВАНИЕ"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    messageBuilder.text(args.joinToString(" ")).send(event)
  }
}
