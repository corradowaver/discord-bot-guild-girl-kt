package com.corradowaver.bot.commands.meme

import com.corradowaver.bot.commands.Command
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import khttp.responses.Response
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component


@Component
class MemeCommand(val messageBuilder: MemeMessage, val memeApiProperties: MemeApiProperties) : Command {
  override val caller: String = "meme"
  override val description = "Meme pics"
  override lateinit var event: GuildMessageReceivedEvent

  override fun invoke(args: List<String>) {
    val post = if (args.isEmpty()) {
      makeRandomPost()
    } else {
      makeRandomPostByTags(args)
    }
    messageBuilder.post(post).send(event)
  }

  private fun makeRandomPost(): Post {
    val response = randomPostResponse()
    print(response)
    val postJson = extractPost(response) ?: run { return makeRandomPost() }
    val imageUrl = extractImageUrl(postJson) ?: run { return makeRandomPost() }
    return Post(url = imageUrl)
  }

  private fun makeRandomPostByTags(tags: List<String>): Post {
    val response = randomPostResponseByTags(tags)
    val postJson = extractPost(response) ?: run { return makeRandomPost() }
    val imageUrl = extractImageUrl(postJson) ?: run { return makeRandomPost() }
    return Post("Meme for ${tags.joinToString(",", "[", "]")}", imageUrl)
  }

  private fun randomPostResponse(): Response {
    return khttp.get("https://api.humorapi.com/memes/random?api-key=${memeApiProperties.token}&keywords=random");
  }

  private fun randomPostResponseByTags(tags: List<String>): Response {
    return khttp.get("https://api.humorapi.com/memes/random?api-key=${memeApiProperties.token}&keywords=${tags.joinToString(separator = "%2C")}")
  }

  private fun extractImageUrl(post: JsonNode): String? {
    return post.get("url")?.toString()?.removePrefix("\"")?.removeSuffix("\"")
      ?: post.get("file_url")?.toString()?.removePrefix("\"")?.removeSuffix("\"")
  }

  private fun extractPost(response: Response): JsonNode? {
    val mapper = ObjectMapper()
    return mapper.readTree(response.content)
  }
}
