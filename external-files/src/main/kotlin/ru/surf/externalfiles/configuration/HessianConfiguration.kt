package ru.surf.externalfiles.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.remoting.caucho.HessianServiceExporter
import org.springframework.remoting.support.RemoteExporter
import ru.surf.externalfiles.service.ResourceFileService
import ru.surf.externalfiles.service.S3FileService


@Configuration
class HessianConfiguration {

    @Bean(name = ["/s3FileServiceApi"])
    fun hessianS3FileService(s3FileService: S3FileService): RemoteExporter {
        val exporter = HessianServiceExporter()
        exporter.service = s3FileService
        exporter.serviceInterface = S3FileService::class.java
        return exporter
    }

   /* @Bean(name = ["/s3ResourceFileServiceApi"])
    fun hessianS3ResourceFileService(resourceFileService: ResourceFileService): RemoteExporter {
        val exporter = HessianServiceExporter()
        exporter.service = resourceFileService
        exporter.serviceInterface = ResourceFileService::class.java
        return exporter
    }*/

}
