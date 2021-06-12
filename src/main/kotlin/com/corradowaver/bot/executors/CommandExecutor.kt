package com.corradowaver.bot.executors

import com.corradowaver.bot.commands.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

object CommandExecutor {
  suspend fun execute(command: Command, event: GuildMessageReceivedEvent, args: List<String> = listOf()) {
      event.channel.sendTyping().queue()
      command.event = event
      command.invoke(args)
  }
}
