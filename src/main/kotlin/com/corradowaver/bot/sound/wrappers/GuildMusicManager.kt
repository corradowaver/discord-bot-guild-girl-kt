package com.corradowaver.bot.sound.wrappers

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager


class GuildMusicManager(manager: AudioPlayerManager) {

  val player: AudioPlayer = manager.createPlayer()

  val scheduler: TrackScheduler = TrackScheduler(player)

  val sendHandler: AudioPlayerSendHandler
    get() = AudioPlayerSendHandler(player)

  init {
    player.addListener(scheduler)
  }
}
