package ru.surf.core.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.surf.auth.service.CredentialsService
import ru.surf.externalfiles.service.ResourceFileService
import ru.surf.externalfiles.service.S3FileService
import ru.surf.remoting.hessian.accessor.HessianProxyFactoryBean


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
    fun credentialsServiceHessianInvoker(): HessianProxyFactoryBean<CredentialsService> = HessianProxyFactoryBean(
            serviceInterface = CredentialsService::class.java,
            serviceUrl = credentialsServiceApi
    )

    @Bean(name = ["s3FileServiceApiHessianInvoker"])
    fun s3FileServiceHessianInvoker(): HessianProxyFactoryBean<S3FileService> = HessianProxyFactoryBean(
            serviceInterface = S3FileService::class.java,
            serviceUrl = s3FileServiceApi
    )

    // TODO: 25.02.2023 Нужна помощь
   /* @Bean(name = ["s3ResourceFileServiceApiHessianInvoker"])
    fun s3ResourceFileServiceHessianInvoker(): HessianProxyFactoryBean {
        val invoker = HessianProxyFactoryBean()
        invoker.serviceUrl = s3ResourceServiceApi
        invoker.serviceInterface = ResourceFileService::class.java
        return invoker
    }*/

}
