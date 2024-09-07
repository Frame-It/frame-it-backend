package com.org.framelt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class VendorConfiguration {
    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .credentialsProvider(InstanceProfileCredentialsProvider.create())
            .region(Region.AP_NORTHEAST_2)
            .build()
    }
}