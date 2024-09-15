package com.org.framelt.portfolio.adapter.out

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.core.sync.RequestBody
import org.springframework.http.MediaType;
import java.util.Optional

@Component
class S3UploadClient(
    private val s3Client: S3Client,
    @Value("\${aws.s3.bucket}") private val bucketName: String,
    @Value("\${aws.s3.key}") private val key: String,
    @Value("\${uploader.image-url}") private val serverImageUrl: String
) : FileUploadClient {

    private val log = LoggerFactory.getLogger(S3UploadClient::class.java)

    override fun upload(fileName: String, mediaType: MediaType, data: ByteArray): Optional<String> {
        return try {
            val request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("$key$fileName")
                .contentType(mediaType.toString())
                .build()
            val requestBody = RequestBody.fromBytes(data)
            val putObjectResponse = s3Client.putObject(request, requestBody)
            log.info("S3 upload response: {}", putObjectResponse)
            Optional.of(getUploadedUrl(fileName))
        } catch (e: Exception) {
            log.error("S3 upload error", e)
            Optional.empty()
        }
    }

    private fun getUploadedUrl(fileName: String): String {
        return "$serverImageUrl$fileName"
    }
}