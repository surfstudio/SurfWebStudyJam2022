package ru.surf.externalfiles.service.impl


import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.entity.S3File
import ru.surf.externalfiles.exception.S3FileNotFoundException
import ru.surf.externalfiles.mapper.S3FileMapper
import ru.surf.externalfiles.repository.S3FileRepository
import ru.surf.externalfiles.service.S3DatabaseService
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.time.ZonedDateTime
import java.util.*

@Service
class S3DatabaseServiceImpl(
    private val s3FileRepository: S3FileRepository,
    private val s3FileMapper: S3FileMapper,
) : S3DatabaseService {

    companion object {
        val logger = LoggerFactory.getLogger(S3DatabaseServiceImpl::class.java)
    }

    override fun saveS3FileData(putObjectRequest: PutObjectRequest, multipartFile: MultipartFile): S3File {
        val s3FileFromRequest =
            s3FileMapper.convertFromS3PutRequestToS3FileEntity(putObjectRequest, multipartFile)
        val s3FileFromDb = multipartFile.originalFilename?.let {
            s3FileRepository.getS3FileByS3Key(putObjectRequest.key())
        }
        return s3FileFromDb?.let {
            logger.debug("Successfully synchronize s3 data $s3FileFromRequest in database")
            synchronizeS3File(it, s3FileFromRequest)
        }
            ?: run {
                logger.debug("Successfully saved s3 data $s3FileFromRequest in database")
                s3FileRepository.save(s3FileFromRequest)
            }
    }

    override fun deleteS3FileData(fileId: UUID): String {
        getS3FileData(fileId).also {
            s3FileRepository.delete(it)
            logger.debug("Successfully deleted s3 data with id $fileId from database")
            return it.s3Key
        }
    }

    override fun getS3FileData(fileId: UUID): S3File {
        val s3FileDataFromDb = s3FileRepository.findByIdOrNull(fileId) ?: throw S3FileNotFoundException(fileId)
        logger.debug("Successfully getting s3 data with id $fileId from database")
        return s3FileDataFromDb
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    override fun keepS3FileData(id: UUID): S3File = getS3FileData(id).run {
        expiresAt = null
        s3FileRepository.save(this)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    override fun processExpiredFiles(zonedDateTime: ZonedDateTime, applyFn: (UUID) -> Unit) {
        s3FileRepository.findByExpiresAtLessThan(zonedDateTime).map { it.id }.forEach(applyFn)
    }

    private fun synchronizeS3File(lastVersion: S3File, newVersion: S3File) =
        lastVersion.apply {
            contentType = newVersion.contentType
            s3Filename = newVersion.s3Filename
            checksum = newVersion.checksum
            s3Key = newVersion.s3Key
            sizeInBytes = newVersion.sizeInBytes
        }

}