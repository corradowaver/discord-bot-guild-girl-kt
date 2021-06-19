package com.corradowaver.bot.commands.joke

import com.corradowaver.bot.commands.Command
import com.corradowaver.bot.sound.wrappers.MusicHandler
import com.corradowaver.bot.tts.TextToSpeechRequester
import com.corradowaver.bot.tts.TextToSpeechRequester.cleanup
import com.corradowaver.bot.tts.TextToSpeechRequester.requestTts
import khttp.responses.Response
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component


@Component
class JokeCommand(val messageBuilder: JokeMessage) : Command {
  override val caller: String = "joke"
  override val description = "Joker 1.0"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    val jokeText = receiveJoke()
    val file = requestTts(jokeText)
    MusicHandler().loadAndPlay(
      event.guild,
      file.toString()
    )
    cleanup(file.toFile())
  }

  private fun receiveJoke(): String {
    val joke = requestJoke().responseToText()
    return if (joke.length < 500) joke else receiveJoke()
  }

  private fun requestJoke() = khttp.get("http://rzhunemogu.ru/RandJSON.aspx?CType=13")
  //TODO different joke types

  private fun Response.responseToText(): String {
    return this.text.substring("\"{\"content:".length)
  }

}
