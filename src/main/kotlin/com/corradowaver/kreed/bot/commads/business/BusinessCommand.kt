package com.corradowaver.kreed.bot.commads.business

import com.corradowaver.kreed.bot.commads.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class BusinessCommand(val messageBuilder: BusinessMessage) : Command {
    override val caller: String = "бизнес"

    override lateinit var event: GuildMessageReceivedEvent

    override fun invoke(args: List<String>) {
        val name = event.author.asTag
        messageBuilder.businessman(name).send(event)
    }
}
