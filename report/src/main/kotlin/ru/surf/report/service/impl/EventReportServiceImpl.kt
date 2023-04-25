package ru.surf.report.service.impl

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import ru.surf.report.model.EventReport
import ru.surf.report.repository.*
import ru.surf.report.service.EventReportService
import ru.surf.report.service.ExternalFilesService
import ru.surf.testing.sharedDto.CandidateScoresResponseDto
import java.util.*

@Service
class EventReportServiceImpl(
    private val teamRepository: TeamRepository,
    private val eventRepository: EventRepository,
    private val candidateRepository: CandidateRepository,
    private val traineeRepository: TraineeRepository,
    private val externalFilesService: ExternalFilesService,
    private val webClient: WebClient,

    @Value("\${services.testing.url}")
    private val testingServiceUrl: String,
    @Value("\${services.external-files.url}")
    private val externalFilesServiceUrl: String,
) : EventReportService {
    private lateinit var testResults: CandidateScoresResponseDto

    override fun getReport(eventId: UUID): EventReport {
        val eventReport = EventReport()

        getTestResult(eventId)

        eventReport.eventDescription = eventRepository.getDescriptionById(eventId)
        eventReport.eventStates = eventRepository.getStatesById(eventId)
        eventReport.peopleAmountByStates = getPeopleAmountByState(eventId)
        eventReport.testResults = getTestResultsByGroup()
        eventReport.teamResults = getTeamsWithAvgScore(eventId)

        return eventReport
    }

    @Transactional
    override fun saveReport(reportByteArray: ByteArray, eventId: UUID) {
        val response = externalFilesService.saveFile(
            reportByteArray,
            "event_report_${eventId}.pdf",
            externalFilesServiceUrl
        )
        eventRepository.updateReportFileId(response.fileId, eventId)
    }

    private fun getTestResult(eventId: UUID) {
        testResults = runBlocking {
            webClient.get()
                .uri("${testingServiceUrl}/test_variant/scores/{eventId}", eventId)
                .retrieve()
                .awaitBody()
        }
    }

    private fun getTestResultsByGroup(): MutableMap<Int, Int> {
        val resultsByGroup = mutableMapOf(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 0
        )
        testResults.scores.forEach {
            when (it.score ?: 0.0) {
                in 0.00..0.25 -> resultsByGroup[0] = resultsByGroup[0]!! + 1
                in 0.26..0.50 -> resultsByGroup[1] = resultsByGroup[1]!! + 1
                in 0.51..0.75 -> resultsByGroup[2] = resultsByGroup[2]!! + 1
                in 0.76..1.00 -> resultsByGroup[3] = resultsByGroup[3]!! + 1
            }
        }
        return resultsByGroup
    }

    private fun getTeamsWithAvgScore(eventId: UUID): MutableMap<String, Double> {
        val teamsWithScore = mutableMapOf<String, Double>()
        teamRepository.getTeamsMentor(eventId).forEach {
            teamsWithScore[it.mentorName] = teamRepository.getTeamScoreById(it.id).average()
        }
        return teamsWithScore
    }

    private fun getPeopleAmountByState(eventId: UUID): MutableMap<Int, Int> {
        val peopleAmountByState = mutableMapOf<Int, Int>()

        peopleAmountByState[0] = candidateRepository.countByEventId(eventId)
        peopleAmountByState[1] = testResults.scores.count { it.score != null }
        peopleAmountByState[2] = traineeRepository.countByEventId(eventId)

        return peopleAmountByState
    }
}