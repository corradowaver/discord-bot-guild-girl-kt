package com.corradowaver.bot.sound.wrappers

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue


class TrackScheduler(private val player: AudioPlayer) : AudioEventAdapter() {
  private val queue: BlockingQueue<AudioTrack> = LinkedBlockingQueue()


  fun queue(track: AudioTrack) {
    if (!player.startTrack(track, true)) {
      queue.offer(track)
    }
  }

  fun nextTrack() {
    player.startTrack(queue.poll(), false)
  }

  override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
    //removing file from temp dir
    if (track.identifier.substringAfterLast("/") != "voting-sound.wav") {
      Files.delete(Path.of(track.identifier))
    }
  }

}
