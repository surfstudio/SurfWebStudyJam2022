package ru.surf.externalfiles.service.impl


import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.entity.S3File
import ru.surf.externalfiles.mapper.S3FileMapper
import ru.surf.externalfiles.repository.S3FileRepository
import ru.surf.externalfiles.service.S3DatabaseService
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Service
class S3DatabaseServiceImpl(
    private val s3FileRepository: S3FileRepository,
    private val s3FileMapper: S3FileMapper,
) : S3DatabaseService {

    override fun saveS3FileData(putObjectRequest: PutObjectRequest, multipartFile: MultipartFile) {
        val s3FileFromRequest =
            s3FileMapper.convertFromS3PutRequestToS3FileEntity(putObjectRequest, multipartFile)
        val s3FileFromDb = multipartFile.originalFilename?.let { s3FileRepository.getS3FileByS3Filename(it) }
        s3FileFromDb?.let { synchronizeS3File(it, s3FileFromRequest) }
            ?: run { s3FileRepository.save(s3FileFromRequest) }
    }

    override fun deleteS3FileData(filename: String) {
        //TODO: поменять исключение
        s3FileRepository.getS3FileByS3Filename(filename)?.let {
            it.also { s3FileRepository.deleteById(it.id) }
        } ?: throw Exception("NoFile")
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