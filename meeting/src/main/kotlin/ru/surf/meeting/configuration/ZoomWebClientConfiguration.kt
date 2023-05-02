package ru.surf.meeting.configuration

import io.netty.channel.ChannelOption
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.time.temporal.ChronoUnit

@Configuration
class ZoomWebClientConfiguration {

    companion object{
        val logger = LoggerFactory.getLogger(ZoomWebClientConfiguration::class.java)
    }

    object Properties {
        const val responseTimeout = 1000L
        // TODO: 05.03.2023 Убрать 20 секунд, снизив до 1-2с
        const val connectionTimeout = 20000
        // TODO: 05.03.2023 Убрать 20 секунд, снизив до 1-2с
        const val readTimeout = 20
        // TODO: 05.03.2023 Убрать 20 секунд, снизив до 1-2с
        const val writeTimeout = 20
        // TODO: 05.03.2023  Убрать 20 секунд, снизив до 1-2с
        const val handshakeTimeout = 20L
    }

    @Bean
    fun zoomWebClient(): WebClient {
        val reactorClientHttp = ReactorClientHttpConnector(
            HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Properties.connectionTimeout)
                .doOnConnected { connection ->
                    connection.addHandlerFirst(
                        ReadTimeoutHandler(
                            Properties.readTimeout,
                        )
                    ).addHandlerFirst(
                        WriteTimeoutHandler(
                            Properties.writeTimeout
                        )
                    )
                }
                .secure { spec ->
                    spec.sslContext(SslContextBuilder.forClient().build())
                        .handshakeTimeout(Duration.ofSeconds(Properties.handshakeTimeout))
                }
                .responseTimeout(Duration.of(Properties.responseTimeout, ChronoUnit.MILLIS))
        )
        return WebClient.builder()
            .clientConnector(reactorClientHttp)
            .defaultHeaders { headers ->
                headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            }
            .filters { filters ->
                filters.add(0, logRequest())
                filters.add(1, logResponse())
            }
            .build()
    }

    private fun logRequest(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofRequestProcessor { clientRequest ->
            logger.trace("Request: ${clientRequest.method()} ${clientRequest.url()}")
            clientRequest.headers()
                .forEach { name, values -> values.forEach { value -> logger.trace("$name=$value") } }
            Mono.just(clientRequest)
        }
    }

    private fun logResponse(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofResponseProcessor { clientResponse ->
            logger.trace("Response status: ${clientResponse.statusCode()}")
            clientResponse.headers().asHttpHeaders()
                .forEach { name, values -> values.forEach { value -> logger.trace("$name=$value") } }
            Mono.just(clientResponse)
        }
    }

}

