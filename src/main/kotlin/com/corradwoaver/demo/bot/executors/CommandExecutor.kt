package com.corradwoaver.demo.bot.executors

import com.corradwoaver.demo.bot.commads.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

object CommandExecutor {
  fun execute(command: Command, event: GuildMessageReceivedEvent, args: List<String> = listOf()) {
    event.channel.sendTyping().queue()
    command.event = event
    command.invoke(args)
  }
}
