package com.corradowaver.bot.sound.wrappers

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.managers.AudioManager


class MyAudioLoadResultHandler(
  val guild: Guild,
  val musicManager: GuildMusicManager,
  val voiceChannel: VoiceChannel? = null
) : AudioLoadResultHandler {

  override fun trackLoaded(track: AudioTrack) {
    play(guild, musicManager, track)
  }

  override fun playlistLoaded(playlist: AudioPlaylist) {
    var firstTrack = playlist.selectedTrack
    if (firstTrack == null) {
      firstTrack = playlist.tracks[0]
    }
    play(guild, musicManager, firstTrack)
  }

  override fun noMatches() {
  }

  override fun loadFailed(exception: FriendlyException) {
  }

  private fun play(guild: Guild, musicManager: GuildMusicManager, track: AudioTrack) {
    if (voiceChannel != null) {
      connectToVoiceChannel(guild.audioManager, voiceChannel)
    } else {
      connectToFirstVoiceChannel(guild.audioManager)
    }
    musicManager.scheduler.queue(track)
  }

  private fun connectToVoiceChannel(audioManager: AudioManager, voiceChannel: VoiceChannel) {
    audioManager.openAudioConnection(voiceChannel)
  }

  private fun connectToFirstVoiceChannel(audioManager: AudioManager) {
    if (!audioManager.isConnected) {
      for (voiceChannel in audioManager.guild.voiceChannels) {
        audioManager.openAudioConnection(voiceChannel)
        break
      }
    }
  }

}
