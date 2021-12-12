package com.corradowaver.bot.executors.upload

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions.EU_CENTRAL_1
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.corradowaver.bot.properties.AwsProperties
import org.springframework.stereotype.Component
import java.io.File

@Component
class S3Manager(
  awsProperties: AwsProperties
) {

  private val sharedFilesBucketName = "guild-girl-shared-files"
  private val greetingsSoundsBucketName = "guild-girl-voice-channel-greetings"
  private val s3Client: AmazonS3

  init {
    val awsCreds = BasicAWSCredentials(awsProperties.accessKeyId, awsProperties.secretKeyId)
    s3Client = AmazonS3ClientBuilder.standard()
      .withRegion(EU_CENTRAL_1)
      .withCredentials(AWSStaticCredentialsProvider(awsCreds))
      .build()

    if (!s3Client.doesBucketExistV2(sharedFilesBucketName)) {
      s3Client.createBucket(sharedFilesBucketName)
    }
  }

  fun uploadToS3(name: String, file: File) =
    s3Client.putObject(sharedFilesBucketName, name, file)

  fun getRandomGreeting() =
    s3Client.listObjects(greetingsSoundsBucketName).objectSummaries.random().key.let { fileName ->
      s3Client.getObject(greetingsSoundsBucketName, fileName)
    }
}
