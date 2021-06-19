package com.corradowaver.bot.tts

import com.corradowaver.bot.tts.Languages.RU
import khttp.responses.Response
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Path
import java.util.UUID
import javax.sound.sampled.AudioFileFormat.Type.WAVE
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteExisting
import kotlin.io.path.deleteIfExists


class TextToSpeechRequester(
  private val yandexAuth: YandexAuth
) {

  private val yandexTtsSynthesizeUrl = "https://tts.api.cloud.yandex.net/speech/v1/tts:synthesize"
  private val sampleRate = 48000
  private val sampleSize = 16

  fun requestTts(text: String): Path {
    val content = requestRawFile(text).content
    val fileName = UUID.randomUUID().toString()
    return saveAsWavFile(fileName, content)
  }

  fun cleanup(file: File) {
    file.delete()
  }

  private fun saveAsWavFile(fileName: String, content: ByteArray): Path {
    val format = AudioFormat(sampleRate.toFloat(), sampleSize, 1, true, false)
    val ais = AudioInputStream(
      ByteArrayInputStream(content), format,
      (content.size / format.frameSize).toLong()
    )
    val file = File(fileName)
    AudioSystem.write(ais, WAVE, file)
    return file.toPath()
  }

  private fun requestRawFile(text: String, language: Languages = RU): Response {

    val values = mapOf(
      "text" to text,
      "lang" to language.id,
      "voice" to "jane",
      "emotion" to "evil",
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
