package ru.surf.auth.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration


@Configuration
class RestTemplateConfig {
    @get:Bean
    val restTemplate: RestTemplate
        get() = RestTemplateBuilder().
                    setConnectTimeout(Duration.ofSeconds(TIMEOUT)).
                    setReadTimeout(Duration.ofSeconds(TIMEOUT)).
                    build()
}

const val TIMEOUT = 3L
