package com.corradowaver.bot.commands.joke

import com.corradowaver.bot.commands.Command
import com.corradowaver.bot.commands.joke.JokeType.CLASSIC
import com.corradowaver.bot.commands.joke.TellTimerHandler.lastJokeSentTimestamp
import com.corradowaver.bot.sound.wrappers.MusicHandler
import com.corradowaver.bot.tts.TextToSpeechConvertor
import com.corradowaver.bot.tts.Voiceover.voice
import khttp.responses.Response
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import org.w3c.dom.Text


@Component
class TellCommand(
  val messageBuilder: TellMessage,
  val ttsConvertor: TextToSpeechConvertor
) : Command {

  override val caller: String = "joke"
  override val description = "Tells you an amazing story from her life"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    if (lastJokeSentTimestamp.get() + 5000 < System.currentTimeMillis()) {
      lastJokeSentTimestamp.set(System.currentTimeMillis())
      messageBuilder.send(event)

      //TODO send emoji choice
      val type = defineJokeType()

      val jokeText = receiveJoke(type)
      voice(event.guild, ttsConvertor, jokeText)

    } else {
      messageBuilder.sendTimeHasNotCome(event)
    }
  }

  private fun defineJokeType(): JokeType {
    return CLASSIC
  }

  private fun receiveJoke(type: JokeType): String {
    val joke = requestJoke(type).responseToText()
    return if (joke.length < 500) joke else receiveJoke(type)
  }

  private fun requestJoke(type: JokeType) = khttp.get("http://rzhunemogu.ru/RandJSON.aspx?CType=${type.id}")

  private fun Response.responseToText(): String {
    return this.text.substring("\"{\"content:".length + 1, this.text.length - 1).replace("\r\n", " ")
  }

}
