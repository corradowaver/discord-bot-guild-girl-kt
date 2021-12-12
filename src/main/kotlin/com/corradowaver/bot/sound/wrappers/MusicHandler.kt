package com.corradowaver.bot.sound.wrappers

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers.registerLocalSource
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers.registerRemoteSources
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.VoiceChannel


class MusicHandler {

  private val musicManagers: MutableMap<Long, GuildMusicManager>
  private val playerManager: AudioPlayerManager

  init {
    musicManagers = HashMap()
    playerManager = DefaultAudioPlayerManager()
    registerRemoteSources(playerManager)
    registerLocalSource(playerManager)
  }

  fun loadAndPlay(guild: Guild, trackUrl: String, voiceChannel: VoiceChannel? = null) {
    val musicManager: GuildMusicManager = getGuildMusicManager(guild)
    playerManager.loadItem(trackUrl, MyAudioLoadResultHandler(guild, musicManager, voiceChannel))
  }

  fun loadAndPlayOrdered(guild: Guild, trackUrl: String) {
    val musicManager: GuildMusicManager = getGuildMusicManager(guild)
    playerManager.loadItemOrdered(musicManager, trackUrl, MyAudioLoadResultHandler(guild, musicManager))
  }

  private fun getGuildMusicManager(guild: Guild): GuildMusicManager {
    val guildId = guild.id.toLong()
    var musicManager = musicManagers[guildId]
    if (musicManager == null) {
      musicManager = GuildMusicManager(playerManager)
      musicManagers[guildId] = musicManager
    }
    guild.audioManager.sendingHandler = musicManager.sendHandler
    return musicManager
  }


}
