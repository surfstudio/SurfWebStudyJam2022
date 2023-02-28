package ru.surf.core.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.remoting.caucho.HessianProxyFactoryBean
import ru.surf.auth.service.CredentialsService
import ru.surf.externalfiles.service.ResourceFileService
import ru.surf.externalfiles.service.S3FileService


@Configuration
class HessianConfiguration(
        @Value("\${authService.credentialsServiceApi}")
        private val credentialsServiceApi: String,

        @Value("\${externalFiles.s3FileServiceApi}")
        private val s3FileServiceApi: String,

        @Value("\${externalFiles.s3ResourceServiceApi}")
        private val s3ResourceServiceApi: String

) {

    @Bean(name = ["credentialsServiceApiInvoker"])
    fun credentialsServiceHessianInvoker(): HessianProxyFactoryBean {
        val invoker = HessianProxyFactoryBean()
        invoker.serviceUrl = credentialsServiceApi
        invoker.serviceInterface = CredentialsService::class.java
        return invoker
    }

    @Bean(name = ["s3FileServiceApiHessianInvoker"])
    fun s3FileServiceHessianInvoker(): HessianProxyFactoryBean {
        val invoker = HessianProxyFactoryBean()
        invoker.serviceUrl = s3FileServiceApi
        invoker.serviceInterface = S3FileService::class.java
        return invoker
    }

    // TODO: 25.02.2023 Нужна помощь
   /* @Bean(name = ["s3ResourceFileServiceApiHessianInvoker"])
    fun s3ResourceFileServiceHessianInvoker(): HessianProxyFactoryBean {
        val invoker = HessianProxyFactoryBean()
        invoker.serviceUrl = s3ResourceServiceApi
        invoker.serviceInterface = ResourceFileService::class.java
        return invoker
    }*/

}
