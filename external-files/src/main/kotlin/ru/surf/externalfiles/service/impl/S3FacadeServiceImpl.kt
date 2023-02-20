package ru.surf.externalfiles.service.impl

import io.klogging.NoCoLogging
import org.springframework.core.io.ByteArrayResource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.PostResponseDto
import ru.surf.externalfiles.mapper.S3FileMapper
import ru.surf.externalfiles.service.S3DatabaseService
import ru.surf.externalfiles.service.S3FacadeService
import ru.surf.externalfiles.service.S3FileService
import ru.surf.externalfiles.service.S3RequestService
import java.sql.SQLException
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class S3FacadeServiceImpl(
    private val s3FileService: S3FileService,
    private val s3DatabaseService: S3DatabaseService,
    private val s3FileMapper: S3FileMapper,
    private val s3RequestService: S3RequestService
) : S3FacadeService {

    companion object : NoCoLogging

    override fun saveFile(file: MultipartFile): PostResponseDto {
        val s3PutRequest = s3RequestService.createS3PutRequest(file)
        val s3File = try {
            s3DatabaseService.saveS3FileData(s3PutRequest, file)
        } catch (e: Exception) {
            val errorMessage = "Database persisting failed for file $file"
            when (e) {
                is SQLException -> {
                    logger.error(errorMessage)
                    throw RuntimeException(e.message)
                }
                else -> {
                    logger.error(errorMessage)
                    throw RuntimeException(e.message)
                }
            }
        }
        s3FileService.putObjectIntoS3Storage(file, s3PutRequest)
        logger.debug("Successfully saving file $file")
        return s3FileMapper.convertFromS3ResumeEntityToPostResponseResumeDto(s3File)
    }

    override fun getFile(fileId: UUID): ByteArrayResource {
        return try {
            s3DatabaseService.getS3FileData(fileId).run {
                ByteArrayResource(s3FileService.getObject(s3RequestService.createS3GetRequest(this.s3Key)))
            }
        } catch (e: Exception) {
            val errorMessage = "Database getting failed for file with id $fileId"
            when (e) {
                is SQLException -> {
                    logger.error(errorMessage)
                    throw RuntimeException(e.message)
                }
                else -> {
                    logger.error(errorMessage)
                    throw RuntimeException(e.message)
                }
            }
        }

    }

    override fun deleteFile(fileId: UUID) {
        val s3KeyDeletedFile = try {
            s3DatabaseService.deleteS3FileData(fileId)
        } catch (e: Exception) {
            val errorMessage = "Database deleting failed for file with id $fileId"
            when (e) {
                is SQLException -> {
                    logger.error(errorMessage)
                    throw RuntimeException(e.message)
                }
                else -> {
                    logger.error(errorMessage)
                    throw RuntimeException(e.message)
                }
            }
        }
        s3RequestService.createS3DeleteRequest(s3KeyDeletedFile).run { s3FileService.deleteObject(this) }
    }

    @Scheduled(fixedDelayString = "\${external-files.claim-interval-seconds}", timeUnit = TimeUnit.SECONDS)
    override fun cleanUnclaimedFiles() {
        s3DatabaseService.processExpiredFiles(ZonedDateTime.now(), this::deleteFile)
    }

}