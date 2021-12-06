package com.corradowaver.bot.commands.vote

import com.corradowaver.bot.commands.MessageBuilder
import com.corradowaver.bot.commands.vote.Reactions.NO
import com.corradowaver.bot.commands.vote.Reactions.YES
import com.corradowaver.bot.sound.wrappers.MusicHandler
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
    return super
      .setTitle("$text | **${resultsMap.maxByOrNull { it.value }?.key?.value ?: "???"}**")
      .setDescription("${YES.value}: ${resultsMap[YES]}%\n${NO.value}: ${resultsMap[NO]}%")
      .setColor(GREEN)
      .build()
  }
}

