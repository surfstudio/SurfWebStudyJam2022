package ru.surf.core.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.surf.auth.service.CredentialsService
import ru.surf.externalfiles.service.S3FacadeService
import ru.surf.externalfiles.service.S3FileService
import ru.surf.meeting.service.ZoomIntegrationService
import ru.surf.remoting.hessian.accessor.HessianProxyFactoryBean


@Configuration
class HessianConfiguration(
        @Value("\${authService.credentialsServiceApi}")
        private val credentialsServiceApi: String,

        @Value("\${externalFiles.s3FileServiceApi}")
        private val s3FileServiceApi: String,

        @Value("\${externalFiles.s3FacadeServiceApi}")
        private val s3FacadeServiceApi: String,

        @Value("\${externalFiles.s3ResourceServiceApi}")
        private val s3ResourceServiceApi: String,

        @Value("\${meeting.zoomServiceApi}")
        private val zoomServiceApi: String

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

    @Bean(name = ["s3FacadeServiceApiHessianInvoker"])
    fun s3FacadeServiceHessianInvoker(): HessianProxyFactoryBean<S3FacadeService> = HessianProxyFactoryBean(
        serviceInterface = S3FacadeService::class.java,
        serviceUrl = s3FacadeServiceApi
    )

    @Bean(name = ["zoomServiceApiHessianInvoker"])
    fun zoomServiceHessianInvoker(): HessianProxyFactoryBean<ZoomIntegrationService> = HessianProxyFactoryBean(
            serviceInterface = ZoomIntegrationService::class.java,
            serviceUrl = zoomServiceApi
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
