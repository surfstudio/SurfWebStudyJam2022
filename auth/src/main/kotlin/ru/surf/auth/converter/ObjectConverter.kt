package ru.surf.auth.converter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@Component
class ObjectConverter(@Autowired private val objectMapper: ObjectMapper) {
    fun convert(obj: Any): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            setAll(objectMapper.convertValue(obj, object : TypeReference<Map<String, String>>(){}))
        }
    }
}
