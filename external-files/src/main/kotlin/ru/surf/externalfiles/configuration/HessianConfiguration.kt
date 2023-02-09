package ru.surf.externalfiles.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.remoting.caucho.HessianServiceExporter
import org.springframework.remoting.support.RemoteExporter
import ru.surf.externalfiles.service.S3FileService


@Configuration
class HessianConfiguration {

    @Bean(name = ["/s3FileServiceApi"])
    fun hessianService(s3FileService: S3FileService): RemoteExporter {
        val exporter = HessianServiceExporter()
        exporter.service = s3FileService
        exporter.serviceInterface = S3FileService::class.java
        return exporter
    }

}
