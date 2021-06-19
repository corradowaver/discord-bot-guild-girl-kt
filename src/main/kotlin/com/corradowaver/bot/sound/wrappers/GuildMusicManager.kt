package com.corradowaver.bot.sound.wrappers

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager


class GuildMusicManager(manager: AudioPlayerManager) {

  val player: AudioPlayer

  val scheduler: TrackScheduler

  val sendHandler: AudioPlayerSendHandler
    get() = AudioPlayerSendHandler(player)

  init {
    player = manager.createPlayer()
    scheduler = TrackScheduler(player)
    player.addListener(scheduler)
  }
}
