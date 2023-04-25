package ru.surf.report.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.surf.report.model.CandidateExcelDto
import ru.surf.report.model.CandidatesReport
import ru.surf.report.repository.CandidateRepository
import ru.surf.report.repository.EventRepository
import ru.surf.report.repository.TraineeRepository
import ru.surf.report.service.CandidateReportService
import ru.surf.report.service.ExternalFilesService
import java.util.*

@Service
class CandidateReportServiceImpl(
    private val candidateRepository: CandidateRepository,
    private val traineeRepository: TraineeRepository,
    private val eventRepository: EventRepository,
    private val externalFilesService: ExternalFilesService,


    @Value("\${services.external-files.url}")
    private val externalFilesServiceUrl: String,
) : CandidateReportService {
    override fun getReport(eventId: UUID): CandidatesReport {
        return CandidatesReport(
            eventId = eventId,
            candidates = getCandidates(eventId)
        )
    }

    @Transactional
    override fun saveReport(reportByteArray: ByteArray, eventId: UUID) {
        val response = externalFilesService.saveFile(
            reportByteArray,
            "candidates_report_for_event_${eventId}.xlsx",
            externalFilesServiceUrl
        )
        eventRepository.updateCandidatesReportFileId(response.fileId, eventId)
    }

    private fun getTags(eventId: UUID): String {
        val tagDescriptions = eventRepository.getEventTags(eventId).map { it.description }
        return tagDescriptions.joinToString(" ")
    }

    private fun getCandidates(eventId: UUID): List<CandidateExcelDto> {
        val idList = traineeRepository.getAllId(eventId)
        val tags = getTags(eventId)
        val candidates = if (idList.isEmpty()) {
            candidateRepository.getApprovedFailedCandidates(eventId)
        } else {
            candidateRepository.getApprovedFailedCandidatesByIdList(idList, eventId)
        }
        return candidates.map { CandidateExcelDto(it.firstName, it.lastName, it.email, tags) }
    }

}