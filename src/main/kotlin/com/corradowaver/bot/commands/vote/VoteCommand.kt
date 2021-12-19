package com.corradowaver.bot.commands.vote

import com.corradowaver.bot.commands.Command
import com.corradowaver.bot.commands.vote.Reactions.NO
import com.corradowaver.bot.commands.vote.Reactions.YES
import com.corradowaver.bot.sound.wrappers.MusicHandler
import net.dv8tion.jda.api.entities.MessageReaction
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.math.RoundingMode.FLOOR

@Component
class VoteCommand(val messageBuilder: VoteMessage) : Command {
  override val caller: String = "vote"
  override val description = "ГОЛОСОВАНИЕ"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    MusicHandler().loadAndPlay(
      event.guild,
      javaClass.classLoader.getResource("voting-sound.wav")!!.path,
      event.member?.voiceState?.channel
    )
    messageBuilder.text(args.joinToString(" ")).send(event)
  }
}

fun countResults(reactions: List<MessageReaction>): Map<Reactions, Float> =
  reactions
    .filter { it.reactionEmote.name == YES.value || it.reactionEmote.name == NO.value }
    .associate { it.reactionEmote.name to it.count - 1 }
    .let {
      val total = (it[YES.value]!! + it[NO.value]!!).toFloat()
      if (total != 0F) {
        mapOf(
          YES to (it[YES.value]!! / total * 100).toBigDecimal().setScale(3, FLOOR).toFloat(),
          NO to (it[NO.value]!! / total * 100).toBigDecimal().setScale(3, FLOOR).toFloat()
        )
      } else {
        mapOf(
          YES to 50.0F,
          NO to 50.0F
        )
      }
    }
