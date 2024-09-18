package com.org.framelt

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class VendorConfiguration(
    @Value("\${aws.s3.access-id}") private val accessId: String,
    @Value("\${aws.s3.secret-key}") private val secretKey: String,
) {
    @Bean
    fun s3Client(): S3Client {
        val credentials = AwsBasicCredentials.create(accessId, secretKey)

        return S3Client
            .builder()
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(Region.AP_NORTHEAST_2)
            .build()
    }
}
