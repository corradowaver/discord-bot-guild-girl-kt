package com.corradowaver.kreed.bot.listeners

import com.corradowaver.kreed.bot.commads.Command
import com.corradowaver.kreed.bot.executors.CommandExecutor.execute
import com.corradowaver.kreed.bot.properties.AppProperties
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class MessageListener(
  rawCommands: List<Command>,
  val properties: AppProperties
) : ListenerAdapter() {

  private val commands: Map<String, Command> = rawCommands.associateBy { it.caller }

  override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
    val args = event.message.contentRaw.split("\\s+")
    val message = args[0].toLowerCase()
    val prefix = properties.prefix
    val preparedArgs = args.drop(1)
    if (message.startsWith(prefix)) {
      val caller = message.subSequence(prefix.length, message.length).toString()
      val command = commands[caller]
      if (command != null) {
        execute(command, event, preparedArgs)
      }
    }
  }
}
