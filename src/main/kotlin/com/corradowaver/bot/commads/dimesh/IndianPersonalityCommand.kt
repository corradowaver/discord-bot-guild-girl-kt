package com.corradowaver.bot.commads.dimesh

import com.corradowaver.bot.commads.Command
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class IndianPersonalityCommand(val messageBuilder: IndianPersonalityMessageBuilder) : Command {
  override val caller: String = "india"
  override val description: String = "Get your Indian personality"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    val responseJson = khttp.get("http://151.248.123.70:8081/india/random").jsonObject
    val indianPersonalityDTO = IndianPersonalityDTO(
      responseJson["indianName"].toString(),
      responseJson["indianSurname"].toString(),
      responseJson["indianNameMeaning"].toString(),
      responseJson["indianSurnameMeaning"].toString()
    )
    messageBuilder.personality(indianPersonalityDTO).send(event)
  }

}
