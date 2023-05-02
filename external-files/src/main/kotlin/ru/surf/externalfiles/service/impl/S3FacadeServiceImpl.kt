package ru.surf.externalfiles.service.impl


import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.PostResponseDto
import ru.surf.externalfiles.mapper.S3FileMapper
import ru.surf.externalfiles.repository.S3FileRepository
import ru.surf.externalfiles.service.S3DatabaseService
import ru.surf.externalfiles.service.S3FacadeService
import ru.surf.externalfiles.service.S3FileService
import ru.surf.externalfiles.service.S3RequestService
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class S3FacadeServiceImpl(
    private val s3FileService: S3FileService,
    private val s3DatabaseService: S3DatabaseService,
    private val s3FileMapper: S3FileMapper,
    private val s3RequestService: S3RequestService,
    private val s3FileRepository: S3FileRepository
) : S3FacadeService {

    companion object {
        val logger = LoggerFactory.getLogger(S3FacadeServiceImpl::class.java)
    }

    override fun saveFile(file: MultipartFile): PostResponseDto {
        val s3PutRequest = s3RequestService.createS3PutRequest(file)
        val s3File = s3DatabaseService.saveS3FileData(s3PutRequest, file)
        try {
            s3FileService.putObjectIntoS3Storage(file, s3PutRequest)
        } catch (e: Exception) {
            val errorMessage = "Saving file $file failed"
            logger.error(errorMessage)
            s3DatabaseService.deleteS3FileData(s3File.id)
            throw RuntimeException(errorMessage)
        }
        logger.info("Successfully saving file $file")
        return s3FileMapper.convertFromS3ResumeEntityToPostResponseResumeDto(s3File)
    }

    override fun getFile(fileId: UUID): ByteArray {
        return try {
            s3DatabaseService.getS3FileData(fileId).run {
                s3FileService.getObject(s3RequestService.createS3GetRequest(this.s3Key))
            }
        } catch (e: Exception) {
            val errorMessage = "Getting failed for file with id $fileId"
            logger.error(errorMessage)
            throw RuntimeException(e.message)
        }
    }

    override fun deleteFile(fileId: UUID) {
        val reserveFile = s3DatabaseService.getS3FileData(fileId)
        val s3KeyDeletedFile = s3DatabaseService.deleteS3FileData(fileId)
        try {
            s3RequestService.createS3DeleteRequest(s3KeyDeletedFile).run { s3FileService.deleteObject(this) }
        } catch (e: Exception) {
            val errorMessage = "Deleting failed for file with id $fileId"
            logger.error(errorMessage)
            s3FileRepository.save(reserveFile)
            throw RuntimeException(e.message)
        }
    }

    //    TODO: Не удалять вообще все файлы (только бесполезные)
    @Scheduled(fixedDelayString = "\${external-files.claim-interval-seconds}", timeUnit = TimeUnit.SECONDS)
    override fun cleanUnclaimedFiles() {
        s3DatabaseService.processExpiredFiles(ZonedDateTime.now(), this::deleteFile)
    }

}