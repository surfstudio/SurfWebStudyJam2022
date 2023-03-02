package ru.surf.testing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.surf.testing.dto.request.AnswerRequestDto
import ru.surf.testing.dto.responce.ScoreInfoResponseDto
import ru.surf.testing.dto.responce.TestInfoResponseDto
import ru.surf.testing.dto.responce.TestVariantResponseDto
import ru.surf.testing.exception.NoSuchEventException
import ru.surf.testing.mapper.TestVariantMapper
import ru.surf.testing.service.TestTemplateService
import ru.surf.testing.service.TestVariantService
import java.util.*

@RestController
@RequestMapping(value = ["/test_variant"])
class TestVariantController(
        @Autowired
        private val testTemplateService: TestTemplateService,

        @Autowired
        private val testVariantService: TestVariantService,

        @Autowired
        private val testVariantMapper: TestVariantMapper
) {

    @PutMapping
    fun create(@RequestParam eventId: UUID,
               @RequestParam candidateId: UUID): ResponseEntity<TestVariantResponseDto> =
            testVariantService.create(
                    testTemplate=testTemplateService.getByEventId(eventId) ?:
                        throw NoSuchEventException(eventId),
                    candidateId=candidateId
            ).let {
                ResponseEntity(testVariantMapper.toDto(it), HttpStatus.CREATED)
            }

    @GetMapping(value = ["/{id}"])
    fun get(@PathVariable id: UUID): ResponseEntity<TestVariantResponseDto> =
            ResponseEntity.ok(testVariantMapper.toDto(testVariantService.get(id)))

    @GetMapping(value = ["/candidate/{candidateId}"])
    fun getByCandidateId(@PathVariable candidateId: UUID): ResponseEntity<TestVariantResponseDto> =
            testVariantService.getByCandidateId(candidateId)?.let {
                ResponseEntity.ok(testVariantMapper.toDto(it))
            } ?: ResponseEntity(HttpStatus.NOT_FOUND)

    @PostMapping(value = ["/start/{testVariantId}"])
    fun startTest(@PathVariable testVariantId: UUID): ResponseEntity<TestInfoResponseDto> =
            ResponseEntity(testVariantMapper.toTestInfoDto(testVariantService.startTest(testVariantId)), HttpStatus.OK)

    @GetMapping(value = ["/info/{testVariantId}"])
    fun getTestInfo(@PathVariable testVariantId: UUID): ResponseEntity<TestInfoResponseDto> =
            ResponseEntity.ok(testVariantMapper.toTestInfoDto(testVariantService.get(testVariantId)))

    @PostMapping(value = ["/"])
    fun submitCurrentAnswerAndMoveNext(
            @RequestBody answerRequestDto: AnswerRequestDto): ResponseEntity<TestInfoResponseDto> =
            ResponseEntity.ok(
                    testVariantMapper.toTestInfoDto(
                            testVariantService.submitCurrentAnswerAndMoveNext(answerRequestDto)
                    )
            )

    @GetMapping(value = ["/score/{candidateId}"])
    fun computeCandidateScore(@PathVariable candidateId: UUID): ResponseEntity<ScoreInfoResponseDto> =
            testVariantService.computeCandidateScore(candidateId).let {
                ResponseEntity.ok(ScoreInfoResponseDto(
                        candidateId=candidateId,
                        score=it
                ))
            }

    @GetMapping(value = ["/scores/{eventId}"])
    fun getCandidateScoresByEventId(@PathVariable eventId: UUID): Any =
            testVariantService.getAllByEventId(eventId).map { candidateInfo ->
                ScoreInfoResponseDto(
                        candidateId=candidateInfo.id,
                        score=testVariantService.computeCandidateScore(candidateInfo.id)
                )
            }

}