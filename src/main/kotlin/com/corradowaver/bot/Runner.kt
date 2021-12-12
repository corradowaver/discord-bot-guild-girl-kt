package com.corradowaver.bot

import com.corradowaver.bot.listeners.FileUploadedListener
import com.corradowaver.bot.listeners.MemberJoinedGuildListener
import com.corradowaver.bot.listeners.MessageListener
import com.corradowaver.bot.properties.AppProperties
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class Runner(
  @Autowired val properties: AppProperties,
  @Autowired val messageListener: MessageListener,
  @Autowired val fileUploadedListener: FileUploadedListener,
  @Autowired val memberJoinedGuildListener: MemberJoinedGuildListener,
) {
  init {
    JDABuilder.createDefault(properties.token)
      .setActivity(Activity.playing(properties.activity))
      .addEventListeners(messageListener)
      .addEventListeners(fileUploadedListener)
      .addEventListeners(memberJoinedGuildListener)
      .build()
  }
}
