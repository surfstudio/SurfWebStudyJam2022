package ru.surf.apigateway.configuration

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouteConfiguration(private val applicationPortsConfiguration: ApplicationPortsConfiguration) {

    object ApplicationNames {
        const val serviceCore: String = "service-core"
        const val externalFiles: String = "service-external-files"
        const val serviceTesting: String = "service-testing"
        const val serviceMeeting: String = "service-meeting"
    }

    @Bean
    fun configureRoutes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route(ApplicationNames.serviceCore) { r ->
                r.path("/core/**")
                    .uri(applicationPortsConfiguration.serviceCorePort)
            }
            .route(ApplicationNames.externalFiles) { r ->
                r.path("/external-files/**")
                    .uri(applicationPortsConfiguration.externalFilesPort)
            }
            .route(ApplicationNames.serviceTesting) { r ->
                r.path("/testing/**")
                    .uri(applicationPortsConfiguration.serviceTestingPort)
            }
            .route(ApplicationNames.serviceMeeting) { r ->
                r.path("/meeting/**")
                    .uri(applicationPortsConfiguration.serviceMeetingPort)
            }
            .build()
    }

}