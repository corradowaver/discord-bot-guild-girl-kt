package com.corradowaver.bot.tts.yandex

import com.corradowaver.bot.tts.TextToSpeechConvertor
import com.corradowaver.bot.tts.yandex.Languages.RU
import khttp.responses.Response
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID
import java.util.UUID.randomUUID
import javax.sound.sampled.AudioFileFormat.Type.WAVE
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem

@Component
class TextToSpeechYandexImpl(
  private val yandexAuth: YandexAuth
) : TextToSpeechConvertor {

  private val yandexTtsSynthesizeUrl = "https://tts.api.cloud.yandex.net/speech/v1/tts:synthesize"
  private val sampleRate = 48000
  private val sampleSize = 16

  override fun requestTts(text: String): Path {
    val content = requestRawFile(text).content
    val fileName = randomUUID().toString()
    return saveAsWavFile(fileName, content)
  }

  private fun saveAsWavFile(fileName: String, content: ByteArray): Path {
    val format = AudioFormat(sampleRate.toFloat(), sampleSize, 1, true, false)
    val ais = AudioInputStream(
      ByteArrayInputStream(content), format,
      (content.size / format.frameSize).toLong()
    )
    val file = Files.createTempFile(fileName, "")
    AudioSystem.write(ais, WAVE, file.toFile())
    return file
  }

  private fun requestRawFile(text: String, language: Languages = RU): Response {

    val values = mapOf(
      "text" to text,
      "lang" to language.id,
      "speed" to "1",
      "voice" to "oksana",
      "emotion" to "good",
      "folderId" to yandexAuth.folder,
      "format" to "lpcm",
      "sampleRateHertz" to sampleRate.toString()
    )

    return khttp.post(
      headers = mapOf("Authorization" to "Bearer ${yandexAuth.iamToken}"),
      data = values,
      url = yandexTtsSynthesizeUrl
    )
  }
}
