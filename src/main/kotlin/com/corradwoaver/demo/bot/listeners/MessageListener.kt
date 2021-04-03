package com.corradwoaver.demo.bot.listeners

import com.corradwoaver.demo.bot.commads.Command
import com.corradwoaver.demo.bot.properties.AppProperties
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MessageListener(
    @Autowired val rawCommands: List<Command>,
    @Autowired val properties: AppProperties
) : ListenerAdapter() {

    private val commands: Map<String, Command> = rawCommands.map { it.caller to it }.toMap()

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        val args = event.message.contentRaw.split("\\s+")
        val message = args[0].toLowerCase()
        val prefix = properties.prefix
        val preparedArgs = args.drop(1)
        if (message.startsWith(prefix)) {
            val caller = message.subSequence(prefix.length, message.length)
            val command = commands[caller]
            if (command != null) {
                event.channel.sendTyping().queue()
                command.event = event
                command.invoke(preparedArgs)
            } else {
                println("Cringe...")
            }
        }
    }
}
