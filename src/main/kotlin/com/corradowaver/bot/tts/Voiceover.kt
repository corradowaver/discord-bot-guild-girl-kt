package com.corradowaver.bot.tts

import com.corradowaver.bot.sound.wrappers.MusicHandler
import net.dv8tion.jda.api.entities.Guild

object Voiceover {
  fun voice(guild: Guild, convertor: TextToSpeechConvertor, text: String) {
    val file = convertor.requestTts(text)
    MusicHandler().loadAndPlay(guild, file.toString())
  }
}
