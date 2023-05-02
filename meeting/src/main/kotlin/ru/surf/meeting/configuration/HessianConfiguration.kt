package ru.surf.meeting.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.surf.meeting.service.ZoomIntegrationService
import ru.surf.remoting.RemoteExporter
import ru.surf.remoting.hessian.exporter.HessianHttpServiceExporter

@Configuration
class HessianConfiguration {

    @Bean(name = ["/zoomServiceApi"])
    fun hessianService(@Autowired zoomIntegrationService: ZoomIntegrationService): RemoteExporter<ZoomIntegrationService> =
            HessianHttpServiceExporter(
                    service = zoomIntegrationService,
                    serviceInterface = ZoomIntegrationService::class.java
            )

}