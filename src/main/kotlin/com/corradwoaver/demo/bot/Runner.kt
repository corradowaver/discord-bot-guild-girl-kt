package com.corradwoaver.demo.bot

import com.corradwoaver.demo.bot.listeners.MessageListener
import com.corradwoaver.demo.bot.properties.AppProperties
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class Runner(
  @Autowired val properties: AppProperties,
  @Autowired val messageListener: MessageListener
) {
  init {
    JDABuilder.createDefault(properties.token)
      .setActivity(Activity.watching(properties.activity))
      .addEventListeners(messageListener)
      .build()
  }
}
