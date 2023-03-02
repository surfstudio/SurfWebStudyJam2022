package ru.surf.testing.configuration

import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter

@Configuration
class JsonMapperConfiguration(
        @Autowired
        private val handlerAdapter: RequestMappingHandlerAdapter
) {

    @EventListener
    fun handleContextRefresh(event: ContextRefreshedEvent): Unit =
            handlerAdapter.messageConverters.forEach {
                if (it is MappingJackson2HttpMessageConverter) {
                    it.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                }
            }

}