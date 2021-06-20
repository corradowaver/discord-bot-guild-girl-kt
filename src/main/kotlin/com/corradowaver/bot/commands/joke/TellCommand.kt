package com.corradowaver.bot.commands.joke

import com.corradowaver.bot.commands.Command
import com.corradowaver.bot.commands.joke.TellTimerHandler.lastJokeSentTimestamp
import com.corradowaver.bot.sound.wrappers.MusicHandler
import com.corradowaver.bot.tts.TextToSpeechConvertor
import khttp.responses.Response
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component


@Component
class TellCommand(
  val messageBuilder: TellMessage,
  val ttsConvertor: TextToSpeechConvertor
) : Command {

  override val caller: String = "tell"
  override val description = "Supporting 3 types of jokes: 'anek', 'story', 'poem' and 18+ tag"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    if (lastJokeSentTimestamp.get() + 5000 < System.currentTimeMillis()) {
      lastJokeSentTimestamp.set(System.currentTimeMillis())
      messageBuilder.send(event)

      val jokeText = receiveJoke(args)
      voice(jokeText)

    } else {
      messageBuilder.sendTimeHasNotCome(event)
    }
  }

  private fun voice(text: String) {
    val file = ttsConvertor.requestTts(text)
    MusicHandler().loadAndPlay(
      event.guild,
      file.toString()
    )
  }

  private fun receiveJoke(args: List<String>): String {

    val matchingClassic = setOf("anek", "joke", "анекдот", "анек")
    val matchingStory = setOf("story", "стори", "история")
    val matchingPoem = setOf("poem", "стих", "стишок")

    var type = when (true) {
      args.any { it in matchingClassic } -> JokeType.CLASSIC
      args.any { it in matchingStory } -> JokeType.STORY
      args.any { it in matchingPoem } -> JokeType.POEM
      else -> JokeType.CLASSIC
    }

    val adult = setOf("adult", "18", "18+", "пошлая", "пошлый")
    if (args.any { it in adult }) {
      type = type.adultVersion()
    }

    val joke = requestJoke(type).responseToText()
    return if (joke.length < 500) joke else receiveJoke(args)
  }

  private fun requestJoke(type: JokeType) = khttp.get("http://rzhunemogu.ru/RandJSON.aspx?CType=${type.id}")

  private fun Response.responseToText(): String {
    return this.text.substring("\"{\"content:".length + 1, this.text.length - 1).replace("\r\n", " ")
  }

}
