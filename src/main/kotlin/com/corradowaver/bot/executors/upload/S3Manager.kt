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

  private val bucketName = "guild-girl-shared-files"
  private val s3Client: AmazonS3

  init {
    val awsCreds = BasicAWSCredentials(awsProperties.accessKeyId, awsProperties.secretKeyId)
    s3Client = AmazonS3ClientBuilder.standard()
      .withRegion(EU_CENTRAL_1)
      .withCredentials(AWSStaticCredentialsProvider(awsCreds))
      .build()

    if (!s3Client.doesBucketExistV2(bucketName)) {
      s3Client.createBucket(bucketName)
    }
  }

  fun uploadToS3(name: String, file: File) =
    s3Client.putObject(bucketName, name, file)

}
