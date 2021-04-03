package com.corradwoaver.demo.bot.commads

import com.corradwoaver.demo.bot.message.MyMessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color

@Component
class Business : Command {
    override val caller: String = "бизнес"

    override var event: GuildMessageReceivedEvent? = null

    override fun invoke(args: List<String>) {
        val name = event!!.author.asTag
        event!!.channel.sendMessage(getMessage(name)).queue()
    }

    private fun getMessage(name: String): MessageEmbed {
        return MyMessageBuilder()
            .setTitle("$name, че за бизнес сука?")
            .setDescription("https://dl-iamt.spbstu.ru/course/index.php?categoryid=5")
            .setImage("https://the-flow.ru/uploads/images/resize/830x0/adaptiveResize/17/40/28/10/86/5838a5f40cd7.png")
            .setColor(Color.BLACK)
            .setFooter("dead by corradowaver")
            .build()
    }
}
