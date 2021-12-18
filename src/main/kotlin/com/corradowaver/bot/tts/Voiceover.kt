package com.corradowaver.bot.tts

import com.corradowaver.bot.sound.wrappers.MusicHandler
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.VoiceChannel

object Voiceover {
  fun voice(guild: Guild, convertor: TextToSpeechConvertor, text: String, voiceChannel: VoiceChannel? = null) {
    val file = convertor.requestTts(text)
    MusicHandler().loadAndPlay(guild, file.toString(), voiceChannel)
  }
}
