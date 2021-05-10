package com.corradowaver.bot.commads.business

import com.corradowaver.bot.commads.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class BusinessCommand(val messageBuilder: BusinessMessage) : Command {
  override val caller: String = "бизнес"
  override val description = "Показывать бизнес ссылка кошка жена"

  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    val name = event.author.asTag
    messageBuilder.businessman(name).send(event)
  }
}
