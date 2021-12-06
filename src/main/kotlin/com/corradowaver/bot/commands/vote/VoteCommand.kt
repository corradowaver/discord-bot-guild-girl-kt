package com.corradowaver.bot.commands.vote

import com.corradowaver.bot.commands.Command
import com.corradowaver.bot.commands.vote.Reactions.NO
import com.corradowaver.bot.commands.vote.Reactions.YES
import com.corradowaver.bot.sound.wrappers.MusicHandler
import net.dv8tion.jda.api.entities.MessageReaction
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class VoteCommand(val messageBuilder: VoteMessage) : Command {
  override val caller: String = "vote"
  override val description = "ГОЛОСОВАНИЕ"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    MusicHandler().loadAndPlay(
      event.guild,
      "C:\\Users\\corra\\Documents\\GitHub\\discord-bot-guild-girl-kt\\src\\main\\resources\\voting-sound.wav"
    )
    messageBuilder.text(args.joinToString(" ")).send(event)
  }
}

fun countResults(reactions: List<MessageReaction>): Map<Reactions, Float> =
  reactions
    .filter { it.reactionEmote.name == YES.value || it.reactionEmote.name == NO.value }
    .associate { it.reactionEmote.name to it.count - 1 }
    .let {
      val total = (it[YES.value]!! + it[NO.value]!!).toFloat() //Removing bots reaction
      mapOf(
        YES to it[YES.value]!! / total * 100,
        NO to it[NO.value]!! / total * 100
      )
    }
