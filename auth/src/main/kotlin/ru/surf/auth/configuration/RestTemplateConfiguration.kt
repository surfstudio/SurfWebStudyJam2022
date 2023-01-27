package ru.surf.auth.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
class RestTemplateConfig {
    @get:Bean
    val restTemplate: RestTemplate
        get() = RestTemplate()
}
