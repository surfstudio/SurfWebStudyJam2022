package ru.surf.auth.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.remoting.caucho.HessianServiceExporter
import org.springframework.remoting.support.RemoteExporter
import ru.surf.auth.service.CredentialsService


@Configuration
class HessianConfiguration {
    @Bean(name = ["/credentialsServiceApi"])
    fun hessianService(credentialsService: CredentialsService): RemoteExporter {
        val exporter = HessianServiceExporter()
        exporter.service = credentialsService
        exporter.serviceInterface = CredentialsService::class.java
        return exporter
    }
}
