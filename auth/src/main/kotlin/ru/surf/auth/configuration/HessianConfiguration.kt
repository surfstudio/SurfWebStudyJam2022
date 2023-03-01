package ru.surf.auth.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.surf.auth.service.CredentialsService
import ru.surf.remoting.RemoteExporter
import ru.surf.remoting.hessian.exporter.HessianHttpServiceExporter


@Configuration
class HessianConfiguration {

    @Bean(name = ["/credentialsServiceApi"])
    fun hessianService(@Autowired credentialsService: CredentialsService): RemoteExporter<CredentialsService> =
            HessianHttpServiceExporter(
                    serviceInterface = CredentialsService::class.java,
                    service = credentialsService
            )

}
