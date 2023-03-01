package ru.surf.testing.service

import ru.surf.testing.dto.request.AnswerRequestDto
import ru.surf.testing.entity.CandidateInfo
import ru.surf.testing.entity.TestTemplate
import ru.surf.testing.entity.TestVariant
import java.util.*

interface TestVariantService {

    fun create(testTemplate: TestTemplate, candidateId: UUID): TestVariant

    fun startTest(testVariantId: UUID): TestVariant

    fun submitCurrentAnswerAndMoveNext(answerRequestDto: AnswerRequestDto): TestVariant

    fun computeCandidateScore(candidateId: UUID): Double?

    fun get(testVariantId: UUID): TestVariant

    fun getByCandidateId(candidateId: UUID): TestVariant?

    fun getAll(): List<TestVariant>

    fun getAllByEventId(eventID: UUID): List<CandidateInfo>
}