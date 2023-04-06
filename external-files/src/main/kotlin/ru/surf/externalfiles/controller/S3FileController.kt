package ru.surf.externalfiles.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.PostResponseDto
import ru.surf.externalfiles.service.S3FacadeService
import java.util.UUID

//TODO: реализовать привязку файла к пользователю
//Пока непонятно в каком виде придет запрос из сервиса core

@RestController
@RequestMapping("/files")
class S3FileController(private val s3FacadeService: S3FacadeService) {

    @PostMapping("/file")
    fun uploadFile(@RequestParam(name = "file") multipartFile: MultipartFile): ResponseEntity<PostResponseDto> =
        ResponseEntity.ok(s3FacadeService.saveFile(multipartFile))

    //    TODO: Сделать автоматическую загрузку файла при вызове эндпоинта
    @GetMapping("/{id}")
    fun downloadFile(@PathVariable(name = "id") id: UUID): ResponseEntity<ByteArray> =
        ResponseEntity.ok(s3FacadeService.getFile(id))

    @DeleteMapping("/{id}")
    fun deleteFile(@PathVariable(name = "id") id: UUID) = s3FacadeService.deleteFile(id)

}