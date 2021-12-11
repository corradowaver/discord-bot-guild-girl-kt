package com.corradowaver.bot.listeners

import com.corradowaver.bot.executors.upload.FileUploader
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class FileUploadedListener(
  val fileUploader: FileUploader
) : ListenerAdapter() {

  override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
    if (!event.author.isBot && event.message.attachments.isNotEmpty()) {
      fileUploader.upload(event.author.asTag, event.message.attachments)
    }
  }

}
