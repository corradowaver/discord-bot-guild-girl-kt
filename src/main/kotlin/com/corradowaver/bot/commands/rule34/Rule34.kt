package com.corradowaver.bot.commands.rule34

import com.corradowaver.bot.commands.Command
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import khttp.responses.Response
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class Rule34Command(val messageBuilder: Rule34Message) : Command {
  override val caller: String = "r34"
  override val description = "Rule 34 pics"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    messageBuilder.post(makePost()).send(event)
  }

  private fun makePost(): Post {
    val response = randomPostResponse()
    val postJson = extractPost(response) ?: run { return makePost() }
    val imageUrl = extractImageUrl(postJson) ?: run { return makePost() }
    return Post(imageUrl)
  }

  private fun randomPostResponse(): Response {
    val id = Random.nextInt(1, 2000000)
    return khttp.get("https://rule34.xxx/index.php?page=dapi&s=post&q=index&id=$id")
  }

  private fun extractImageUrl(post: JsonNode): String? {
    return post.get("sample_url")?.toString()?.removePrefix("\"")?.removeSuffix("\"")
      ?: post.get("file_url")?.toString()?.removePrefix("\"")?.removeSuffix("\"")
  }

  private fun extractPost(response: Response): JsonNode? = XmlMapper().readTree(response.content).get("post")
}
