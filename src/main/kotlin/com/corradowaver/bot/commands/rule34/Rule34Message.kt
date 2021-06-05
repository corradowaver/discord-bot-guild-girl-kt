package com.corradowaver.bot.commands.rule34

import com.corradowaver.bot.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
final object Rule34Message : MessageBuilder() {

  private lateinit var post: Post
  fun post(post: Post) = apply { this.post = post }

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setTitle(post.name)
      .setDescription("")
      .setImage(post.url)
      .build()
  }
}
