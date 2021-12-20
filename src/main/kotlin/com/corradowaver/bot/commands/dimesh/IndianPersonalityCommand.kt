package com.corradowaver.bot.commands.dimesh

import com.corradowaver.bot.commands.Command
import khttp.get
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component

@Component
class IndianPersonalityCommand(val messageBuilder: IndianPersonalityMessageBuilder) : Command {
  override val caller: String = "india"
  override val description: String = "Get your Indian personality. Be careful you may become Rajesh"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    val indianPersonalityDTO = generateIndianPersonality()
    event.message.member?.modifyNickname("${indianPersonalityDTO.name} ${indianPersonalityDTO.surname}")?.queue()
    messageBuilder.personality(indianPersonalityDTO).send(event)
  }
}

fun generateIndianPersonality(): IndianPersonalityDTO {
  val responseJson = get("http://185.46.8.32:8081/india/random").jsonObject
  return IndianPersonalityDTO(
    responseJson["indianName"].toString(),
    responseJson["indianSurname"].toString(),
    responseJson["indianNameMeaning"].toString(),
    responseJson["indianSurnameMeaning"].toString()
  )
}
