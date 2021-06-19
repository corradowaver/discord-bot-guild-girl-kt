package com.corradowaver.bot.commands.help

import com.corradowaver.bot.commands.MessageBuilder
import com.corradowaver.bot.commands.Command
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color.CYAN

@Component
final object HelpMessage : MessageBuilder() {

  private var commands = listOf<Command>()
  fun commands(commands: List<Command>) = apply { this.commands = commands }

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setTitle("Guild-Girl Help Center")
      .setDescription(createDescription(commands))
      .setColor(CYAN)
      .setFooter("made by corradowaver")
      .build()
  }

  private fun createDescription(commands: List<Command>): String =
    commands.joinToString(separator = "\n") { "`${it.caller}` : _${it.description}_" }
}
