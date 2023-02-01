package ru.surf.externalfiles.util

import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.enum.FileExtension

//TODO: Возможно сделать отдельный сервис для этого,
// увеличить количество допустимых форматов, продумать иерархию файлов

fun getS3Path(multipartFile: MultipartFile): String = multipartFile.getFolderType() + multipartFile.originalFilename

fun MultipartFile.getFolderType(): String {
    //TODO: поменять исключение
    val fileType = contentType ?: throw RuntimeException()
    return when {
        fileType.uppercase() in FileExtension.IMAGE.extensions -> "images/"
        fileType.uppercase() in FileExtension.DOCUMENT.extensions -> "documents/"
        //TODO: поменять исключение
        else -> throw RuntimeException("NO FOUND TYPE FOR:$fileType")
    }
}
