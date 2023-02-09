package ru.surf.externalfiles.controller

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.service.S3FileService
import java.util.UUID

//TODO: реализовать привязку файла к пользователю
//Пока непонятно в каком виде придет запрос из сервиса core

@RestController
@RequestMapping("/files")
class S3FileController(private val s3FileService: S3FileService) {

    @PostMapping("/file")
    fun uploadFile(@RequestParam(name = "file") multipartFile: MultipartFile) =
        s3FileService.putObjectIntoS3Storage(multipartFile)

    @GetMapping("")
    fun downloadFile(@RequestParam(name = "filename") objectName: String): ResponseEntity<ByteArrayResource> {
        val bytes = s3FileService.getObject(objectName)
        val byteArrayResource = ByteArrayResource(bytes)
        return ResponseEntity.ok(byteArrayResource)
    }

    @DeleteMapping("/file/{file_id}")
    fun deleteFile(@PathVariable(name = "file_id") fileId: UUID) = s3FileService.deleteObject(fileId)

}