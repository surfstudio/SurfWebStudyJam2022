package ru.surf.externalfiles.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties(prefix = "s3")
data class S3PropertiesConfiguration(
    val accessKey: String,
    val secretKey: String,
    val bucketName: String,
    val url: String,
    val host: Int
) {
    val fullUrlAddress: String = "$url:$host"
}
