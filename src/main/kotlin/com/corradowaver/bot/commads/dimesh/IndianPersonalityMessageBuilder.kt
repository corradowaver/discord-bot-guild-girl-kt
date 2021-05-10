package com.corradowaver.bot.commads.dimesh

import com.corradowaver.bot.MessageBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.Color

@Component
final object IndianPersonalityMessageBuilder: MessageBuilder() {

  private lateinit var personality: IndianPersonalityDTO
  fun personality(personalityObject: IndianPersonalityDTO) = apply { personality = personalityObject }

  override fun send(event: GuildMessageReceivedEvent) {
    event.channel.sendMessage(createMessage()).queue()
  }

  override fun createMessage(): MessageEmbed {
    return super
      .setCustomPrefixedTitle(":flag_in:", "हेलो एलीकुम")
      .setDescription(createDescription())
      .setImage(randomImage())
      .setColor(Color.ORANGE)
      .build()
  }

  private fun createDescription(): String =
    "**${personality.name.capitalize()}** - _${personality.nameMeaning.capitalize()}_\n" +
        "**${personality.surname.capitalize()}** - _${personality.surnameMeaning.capitalize()}_\n"


  private fun randomImage(): String {
    val images = listOf(
      "https://previews.123rf.com/images/dragonimages/dragonimages1703/dragonimages170309431/74810112-pensive-indian-programmer-with-hands-behind-his-head-looking-at-the-screen.jpg",
      "http://s.pfst.net/2015.05/780408388068ad6d408027d5e0243867f1dce428325_b.jpg",
      "https://previews.123rf.com/images/dragonimages/dragonimages1703/dragonimages170309433/74810116-tired-indian-programmer-looking-at-computer-screen.jpg",
      "https://st2.depositphotos.com/1643295/7439/i/950/depositphotos_74390495-stock-photo-serious-indian-coder.jpg",
      "https://thumbs.dreamstime.com/b/programmer-working-laptop-portrait-smiling-indian-programmer-working-laptop-office-desk-200015841.jpg",
      "https://st4.depositphotos.com/1643295/27583/i/1600/depositphotos_275833776-stock-photo-portrait-indian-programmer-eyeglasses-sitting.jpg",
      "https://st4.depositphotos.com/1643295/28196/i/1600/depositphotos_281969830-stock-photo-portrait-indian-young-office-worker.jpg",
      "https://st4.depositphotos.com/1643295/28033/i/450/depositphotos_280332052-stock-photo-indian-office-worker-has-conversation.jpg"
    )
    return images.random()
    }
  }
