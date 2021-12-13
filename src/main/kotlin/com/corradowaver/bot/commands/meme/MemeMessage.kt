package com.corradowaver.bot.commands.meme

import com.corradowaver.bot.commands.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
final object MemeMessage : MessageBuilder() {

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
