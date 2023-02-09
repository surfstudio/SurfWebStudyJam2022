package ru.surf.externalfiles.service.impl

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.surf.core.entity.Candidate
import ru.surf.externalfiles.dto.PostResponseResumeDto
import ru.surf.externalfiles.mapper.S3FileMapper
import ru.surf.externalfiles.service.ResourceFileService
import ru.surf.externalfiles.service.S3FileService
import java.io.ByteArrayInputStream

@Service
class ResourceFileServiceImpl(
    private val s3FileService: S3FileService,
    private val s3FileMapper: S3FileMapper
) : ResourceFileService {

    override fun parseHrExcelFile(): List<Candidate> {
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
    }

    override fun saveResume(resume: MultipartFile): PostResponseResumeDto {

        val s3ResumeFile = s3FileService.putObjectIntoS3Storage(resume)
        return s3FileMapper.convertFromS3ResumeEntityToPostResponseResumeDto(s3ResumeFile)
    }

    private fun mapExcelBoolean(boolean: String): Boolean =
        when {
            boolean.equals("да", true) -> true
            boolean.equals("нет", true) -> false
            else -> false
        }

}

