package com.corradowaver.bot.commands.vote

import com.corradowaver.bot.commands.MessageBuilder
import com.corradowaver.bot.commands.vote.Reactions.IDK
import com.corradowaver.bot.commands.vote.Reactions.NO
import com.corradowaver.bot.commands.vote.Reactions.YES
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.MessageEmbed
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
          it.addReaction(IDK.value).queue()
          delay(15_000L)
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

  private fun createVoteResultsMessage(resultsMap: Map<Reactions, Float>): MessageEmbed {
    val winner = resultsMap.values.sorted().takeLast(2).let { twoEntries ->
      if (twoEntries[0] == twoEntries[1]) {
        IDK.value
      } else {
        resultsMap.entries.find {
          it.value == twoEntries[1]
        }?.key?.value
      }
    }
    return super
      .setTitle("$text | $winner")
      .setDescription("${YES.value}: ${resultsMap[YES]}%\n${NO.value}: ${resultsMap[NO]}%\n${IDK.value}: ${resultsMap[IDK]}%")
      .setColor(GREEN)
      .build()
  }
}

