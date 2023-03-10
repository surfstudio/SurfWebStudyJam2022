package ru.surf.report.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import ru.surf.report.model.Report
import ru.surf.report.repository.*
import ru.surf.testing.sharedDto.CandidateScoresResponseDto
import java.util.*

@Service
class ReportServiceImpl(
    private val teamRepository: TeamRepository,
    private val eventRepository: EventRepository,
    private val candidateRepository: CandidateRepository,
    private val traineeRepository: TraineeRepository,
    private val webClient: WebClient,
) : ReportService {
    private lateinit var testResults: CandidateScoresResponseDto

    override fun getPdfReport(eventId: UUID): Report {
        val report = Report()

        getTestResult(eventId)

        report.eventDescription = eventRepository.getDescriptionById(eventId)
        report.eventStates = eventRepository.getStatesById(eventId)
        report.peopleAmountByStates = getPeopleAmountByState(eventId)
        report.testResults = getTestResultsByGroup()
        report.teamResults = getTeamsWithAvgScore(eventId)

        return report
    }

    private fun getTestResult(eventId: UUID) {
        testResults = runBlocking {
            webClient.get()
                .uri("/test_variant/scores/{eventId}", eventId)
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
        testResults.scores.forEach() {
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