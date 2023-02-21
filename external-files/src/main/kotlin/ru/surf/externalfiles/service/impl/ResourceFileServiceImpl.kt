package ru.surf.externalfiles.service.impl

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.PostResponseDto
import ru.surf.externalfiles.mapper.S3FileMapper
import ru.surf.externalfiles.service.ResourceFileService
import ru.surf.externalfiles.service.S3FacadeService

@Service
class ResourceFileServiceImpl(
    private val s3FacadeService: S3FacadeService,
    private val s3FileMapper: S3FileMapper,
) : ResourceFileService {

    // TODO: 09.02.2023  Уточнить позже вид excel файла у HR

    /* override fun parseHrExcelFile(): List<Candidate> {
         //TODO: сделать рефакторинг хардкода
         val bytes = s3FileService.getObject("documents/Кандидаты (2).xlsx")
         val sheet: XSSFSheet = XSSFWorkbook(ByteArrayInputStream(bytes)).getSheet("Кандидаты")
         return (1..sheet.lastRowNum).map { sheet.getRow(it) }.map {
             Candidate(
                 //TODO 07.02.2023 Поменялась сущность кандидата, сделать рефакторинг позже.
                 //name = it.getCell(0).stringCellValue,
                 email = it.getCell(4).stringCellValue,
                 isNew = mapExcelBoolean(it.getCell(7).stringCellValue),
             )
         }.toList()
     }*/

    override fun saveResume(resume: MultipartFile): PostResponseDto = s3FacadeService.saveFile(resume)

    private fun mapExcelBoolean(boolean: String): Boolean =
        when {
            boolean.equals("да", true) -> true
            boolean.equals("нет", true) -> false
            else -> false
        }

}

