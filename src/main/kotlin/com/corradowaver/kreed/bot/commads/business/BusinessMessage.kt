package com.corradowaver.kreed.bot.commads.business

import com.corradowaver.kreed.bot.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color

@Component
final object BusinessMessage : MessageBuilder() {

  private var businessman = ""
  fun businessman(name: String) = apply { businessman = name }

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setTitle("$businessman, че за бизнес сука?")
      .setDescription("https://dl-iamt.spbstu.ru/course/index.php?categoryid=5")
      .setImage("https://the-flow.ru/uploads/images/resize/830x0/adaptiveResize/17/40/28/10/86/5838a5f40cd7.png")
      .setColor(Color.BLACK)
      .setFooter("dead by corradowaver")
      .build()
  }

}
