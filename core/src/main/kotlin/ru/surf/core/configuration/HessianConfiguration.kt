package ru.surf.core.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.remoting.caucho.HessianProxyFactoryBean
import ru.surf.auth.service.CredentialsService


@Configuration
class HessianConfiguration(
        @Value("\${authService.credentialsServiceApi}")
        private val hessianApi: String
) {
    @Bean(name = ["credentialsServiceApiInvoker"])
    fun hessianInvoker(): HessianProxyFactoryBean {
        val invoker = HessianProxyFactoryBean()
        invoker.serviceUrl = hessianApi
        invoker.serviceInterface = CredentialsService::class.java
        return invoker
    }
}
