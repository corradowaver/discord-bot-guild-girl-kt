package com.corradwoaver.demo.bot.commads.cringe

import com.corradwoaver.demo.bot.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color

@Component
final object CringeMessage : MessageBuilder() {
  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setTitle("Кринжулька")
      .setDescription("https://dl-iamt.spbstu.ru/course/index.php?categoryid=5")
      .setImage("https://prostars.org/files/catalog/items/0/530x800/5/5d9ee92139ccd.png")
      .setColor(Color.BLACK)
      .setFooter("deploytest")
      .build()
  }
}
