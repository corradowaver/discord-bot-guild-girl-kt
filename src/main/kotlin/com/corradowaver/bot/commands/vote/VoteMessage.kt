package com.corradowaver.bot.commands.vote

import com.corradowaver.bot.commands.MessageBuilder
import com.corradowaver.bot.commands.vote.Reactions.NO
import com.corradowaver.bot.commands.vote.Reactions.YES
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.MessageReaction
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color.CYAN
import java.awt.Color.GREEN

@Component
final object VoteMessage : MessageBuilder() {

  private lateinit var text: String
  fun text(text: String) = apply { this.text = text }

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue {
      runBlocking {
        launch {
          it.addReaction(YES.value).queue()
          it.addReaction(NO.value).queue()
          delay(5_000L)
          event.channel.retrieveMessageById(it.id).queue { message ->
            message.editMessage(createVoteResultsMessage(countResults(message.reactions))).queue()
          }
        }
      }

    }
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setTitle(text)
      .setDescription("")
      .setColor(CYAN)
      .build()
  }

  fun createVoteResultsMessage(resultsMap: Map<Reactions, Float>): MessageEmbed {
    return super
      .setTitle(text)
      .setDescription(resultsMap.entries.joinToString())
      .setColor(GREEN)
      .build()
  }

  fun countResults(reactions: List<MessageReaction>): Map<Reactions, Float> =
    reactions
      .filter { it.reactionEmote.name == YES.value || it.reactionEmote.name == NO.value }
      .associate { it.reactionEmote.name to it.count }
      .let {
        val total = it[YES.value]!! + it[NO.value]!! - 2F //Removing bots reaction
        mapOf(
          YES to (it[YES.value]!! / total),
          NO to (it[NO.value]!! / total)
        )
      }
}

