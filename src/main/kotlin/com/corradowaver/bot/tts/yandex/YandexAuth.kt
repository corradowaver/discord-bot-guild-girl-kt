package com.corradowaver.bot.tts.yandex

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class YandexAuth(yandexCloudProperties: YandexCloudProperties) {

  val yandexIamTokenUrl = "https://iam.api.cloud.yandex.net/iam/v1/tokens"

  var folder = yandexCloudProperties.folder
  var iamToken: String = ""

  private var yandexPassportOAuthToken = yandexCloudProperties.token


  @Scheduled(fixedRate = 6 * 60 * 60 * 1000, initialDelay = 1000)
  fun scheduleMe() {
    iamToken = requestIamToken()
  }

  private fun requestIamToken(): String =
    khttp.get(
      url = yandexIamTokenUrl,
      data = "{\"yandexPassportOauthToken\":\"$yandexPassportOAuthToken\"}"
    )
      .jsonObject.get("iamToken")
      .toString()

}
