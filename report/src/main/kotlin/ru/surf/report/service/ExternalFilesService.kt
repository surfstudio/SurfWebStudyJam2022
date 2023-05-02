package ru.surf.report.service

import ru.surf.report.model.PostResponseDto

interface ExternalFilesService {
    fun saveFile(fileByteArray: ByteArray, fileName: String, resourceUri: String): PostResponseDto
}