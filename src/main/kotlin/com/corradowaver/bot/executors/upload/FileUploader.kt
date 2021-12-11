package com.corradowaver.bot.executors.upload

import net.dv8tion.jda.api.entities.Message.Attachment
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files.createTempDirectory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

@Component
class FileUploader(
  val s3Manager: S3Manager
) {

  private val tempDirPath = createTempDirectory("guild-girl-uploaded-files")

  fun upload(sender: String, attachments: List<Attachment>) {
    attachments.forEach { attachment ->
      attachment.downloadToFile("${tempDirPath.toAbsolutePath()}\\${attachment.fileName}")
        .thenApply { file ->
          s3Manager.uploadToS3(composeFileName(sender, file), file)
          file
        }.thenApply { file ->
          file.delete()
        }
    }
  }

  private fun composeFileName(sender: String, file: File) =
    "$sender/${file.nameWithoutExtension}(${LocalDateTime.now().format(ofPattern("yyyy-MM-dd_HH:mm:ss"))}).${file.extension}"

}
