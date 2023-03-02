package ru.surf.externalfiles.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.surf.externalfiles.service.S3FileService
import ru.surf.remoting.RemoteExporter
import ru.surf.remoting.hessian.exporter.HessianHttpServiceExporter


@Configuration
class HessianConfiguration {

    @Bean(name = ["/s3FileServiceApi"])
    fun hessianService(@Autowired s3FileService: S3FileService): RemoteExporter<S3FileService> =
            HessianHttpServiceExporter(
                    service = s3FileService,
                    serviceInterface = S3FileService::class.java
            )

   /* @Bean(name = ["/s3ResourceFileServiceApi"])
    fun hessianS3ResourceFileService(resourceFileService: ResourceFileService): RemoteExporter {
        val exporter = HessianServiceExporter()
        exporter.service = resourceFileService
        exporter.serviceInterface = ResourceFileService::class.java
        return exporter
    }*/

}
