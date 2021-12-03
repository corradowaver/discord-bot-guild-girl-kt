package com.corradowaver.bot.commands.prefix

import com.corradowaver.bot.commands.Command
import com.corradowaver.bot.properties.AppProperties
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class PrefixCommand(
  val messageBuilder: PrefixMessage,
  val appProperties: AppProperties
) : Command {
  override val caller: String = "prefix"
  override val description = "Changes prefix - [prefix]prefix <new_prefix>"
  override lateinit var event: GuildMessageReceivedEvent

  companion object {
    var prefix: String? = null
  }

  init {
    prefix = appProperties.prefix
  }

  override fun invoke(args: List<String>) {

    when (args.size) {
      0 -> {
        messageBuilder.prefix(prefix ?: appProperties.prefix).send(event)
      }
      1 -> {
        appProperties.prefix = args.first()
        prefix = args.first()
        messageBuilder.prefix(prefix ?: appProperties.prefix).sendPrefixChanged(event)
      }
      else -> {
        messageBuilder.prefix(prefix ?: appProperties.prefix).sendErrorMessage(event)
      }
    }
  }
}
