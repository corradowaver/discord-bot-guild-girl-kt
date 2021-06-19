package com.corradowaver.bot.sound.wrappers

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent


object GuildJoiner {

  fun join(event: GuildMessageReceivedEvent) {
    val channel = event.channel
    val audioManager = event.guild.audioManager
    if (audioManager.isConnected) {
      channel.sendMessage("Already here").queue()
      return;
    }
    val memberVoiceState = event.member!!.voiceState!!
    if (!memberVoiceState.inVoiceChannel()) {
      channel.sendMessage("Join a voice channel first").queue()
      return
    }
    val voiceChannel: VoiceChannel? = memberVoiceState.channel
    val selfMember: Member = event.guild.selfMember
    if (!voiceChannel?.let { selfMember.hasPermission(it, Permission.VOICE_CONNECT) }!!) {
      channel.sendMessage("Missing permission to join").queue()
      return
    }
    audioManager.openAudioConnection(voiceChannel)
    channel.sendMessage("Joined").queue()
  }
}
