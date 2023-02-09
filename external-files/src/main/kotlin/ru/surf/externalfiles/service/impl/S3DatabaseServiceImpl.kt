package ru.surf.externalfiles.service.impl


import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.entity.S3File
import ru.surf.externalfiles.exception.S3FileNotFoundException
import ru.surf.externalfiles.mapper.S3FileMapper
import ru.surf.externalfiles.repository.S3FileRepository
import ru.surf.externalfiles.service.S3DatabaseService
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*

@Service
class S3DatabaseServiceImpl(
    private val s3FileRepository: S3FileRepository,
    private val s3FileMapper: S3FileMapper,
) : S3DatabaseService {

    override fun saveS3FileData(putObjectRequest: PutObjectRequest, multipartFile: MultipartFile): S3File {
        val s3FileFromRequest =
            s3FileMapper.convertFromS3PutRequestToS3FileEntity(putObjectRequest, multipartFile)
        val s3FileFromDb = multipartFile.originalFilename?.let {
            s3FileRepository.getS3FileByChecksum(
                DigestUtils.sha256Hex(multipartFile.inputStream)
            )
        }
        return s3FileFromDb?.let { synchronizeS3File(it, s3FileFromRequest) }
            ?: run { s3FileRepository.save(s3FileFromRequest) }
    }

    override fun deleteS3FileData(filename: String) {
        s3FileRepository.getS3FileByS3Filename(filename)?.let {
            it.also { s3FileRepository.deleteById(it.id) }
        } ?: throw S3FileNotFoundException(filename)
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    override fun persistS3File(id: UUID): S3File? = s3FileRepository.findById(id).or {
            null
        }.get().run {
            expiresAt = null
            s3FileRepository.save(this)
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