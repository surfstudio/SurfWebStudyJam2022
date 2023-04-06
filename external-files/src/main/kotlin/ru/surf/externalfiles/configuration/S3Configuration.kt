package ru.surf.externalfiles.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Configuration
class S3Configuration {

    @Bean
    fun S3Client(s3PropertiesConfiguration: S3PropertiesConfiguration): S3Client =
        AwsBasicCredentials.create(s3PropertiesConfiguration.accessKey, s3PropertiesConfiguration.secretKey).run {
            S3Client.builder()
                .endpointOverride(URI.create(s3PropertiesConfiguration.fullUrlAddress))
                .region(Region.AWS_GLOBAL)
                .credentialsProvider(StaticCredentialsProvider.create(this))
                .build()
        }
}