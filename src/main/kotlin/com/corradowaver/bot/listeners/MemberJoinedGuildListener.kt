package com.corradowaver.bot.listeners

import com.amazonaws.services.s3.model.S3Object
import com.corradowaver.bot.executors.upload.S3Manager
import com.corradowaver.bot.sound.wrappers.MusicHandler
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files.copy
import java.nio.file.Files.createTempDirectory
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

@Component
class MemberJoinedGuildListener(
  val s3Manager: S3Manager
) : ListenerAdapter() {

  private val tempDirPath = createTempDirectory("guild-girl-greetings")

  override fun onGuildVoiceJoin(event: GuildVoiceJoinEvent) {
    val randomS3Greeting = s3Manager.getRandomGreeting()
    val file = saveS3ObjectToTempDir(randomS3Greeting)
    val voiceChannel = event.member.voiceState?.channel
    MusicHandler().loadAndPlay(
      event.guild,
      file.path,
      voiceChannel
    )
  }

  fun saveS3ObjectToTempDir(randomS3Greeting: S3Object) =
    randomS3Greeting.objectContent.let { inputStream ->
      val targetFile = File("${tempDirPath.toAbsolutePath()}\\${randomS3Greeting.key}")
      copy(inputStream, targetFile.toPath(), REPLACE_EXISTING)
      targetFile
    }

}
