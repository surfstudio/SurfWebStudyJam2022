package ru.surf.report.service.impl

import kotlinx.coroutines.runBlocking
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import ru.surf.report.model.PostResponseDto
import ru.surf.report.service.ExternalFilesService

@Service
class ExternalFilesServiceImpl(
    private val webClient: WebClient
) : ExternalFilesService {
    override fun saveFile(fileByteArray: ByteArray, fileName: String, resourceUri: String): PostResponseDto {
        val builder = MultipartBodyBuilder()
        builder.part("file", ByteArrayResource(fileByteArray))
            .filename(fileName)
            .contentType(MediaType.APPLICATION_PDF)

        val response: PostResponseDto = runBlocking {
            webClient.post()
                .uri(resourceUri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .awaitBody()
        }

        return response
    }
}