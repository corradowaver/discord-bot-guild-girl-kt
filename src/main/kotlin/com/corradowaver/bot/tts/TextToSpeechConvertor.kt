package com.corradowaver.bot.tts

import java.nio.file.Path

interface TextToSpeechConvertor {
  fun requestTts(text: String): Path

}
