package com.corradowaver.bot.tts

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
final object YandexAuth {

  const val yandexIamTokenUrl = "https://iam.api.cloud.yandex.net/iam/v1/tokens"

  var iamToken: String = ""

  private const val yandexPassportOAuthToken = "AQAAAAAzyoYUAATuwdxaa-G0R01CoWSGG6fH-tY"


  @Scheduled(fixedRate = 60 * 60 * 1000, initialDelay = 1000)
  fun scheduleMe() {
    iamToken = requestIamToken()
    println(iamToken)
  }

  private fun requestIamToken(): String =
    khttp.get(
      url = yandexIamTokenUrl,
      data = "{\"yandexPassportOauthToken\":\"$yandexPassportOAuthToken\"}"
    )
      .jsonObject.get("iamToken")
      .toString()

}
