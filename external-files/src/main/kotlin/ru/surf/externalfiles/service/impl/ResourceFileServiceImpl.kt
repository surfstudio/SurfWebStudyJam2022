package ru.surf.externalfiles.service.impl

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.CandidateExcelDto
import ru.surf.externalfiles.dto.PostResponseDto
import ru.surf.externalfiles.service.ResourceFileService
import ru.surf.externalfiles.service.S3FacadeService
import java.io.ByteArrayInputStream
import java.util.*

@Service
class ResourceFileServiceImpl(
    private val s3FacadeService: S3FacadeService,
) : ResourceFileService {

    override fun parseHrExcelFile(fileId: UUID): List<CandidateExcelDto> {
        val excelFile = s3FacadeService.getFile(fileId)
        val sheet: XSSFSheet = XSSFWorkbook(ByteArrayInputStream(excelFile)).getSheet("Кандидаты")
        return (1..sheet.lastRowNum).map { sheet.getRow(it) }.map {
            CandidateExcelDto(
                firstName = it.getCell(0).stringCellValue,
                lastName = it.getCell(1).stringCellValue,
                email = it.getCell(2).stringCellValue,
                tags = it.getCell(3).stringCellValue.split(" ").toSet()
            )
        }.toList()
    }

    override fun saveResume(resume: MultipartFile): PostResponseDto = s3FacadeService.saveFile(resume)

}

