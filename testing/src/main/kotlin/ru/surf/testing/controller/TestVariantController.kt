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
import org.springframework.web.bind.annotation.RestController
import ru.surf.testing.dto.CandidateInfoFullDto
import ru.surf.testing.dto.request.AnswerRequestDto
import ru.surf.testing.sharedDto.CandidateScoreResponseDto
import ru.surf.testing.dto.responce.TestInfoResponseDto
import ru.surf.testing.dto.responce.TestVariantResponseDto
import ru.surf.testing.exception.NoSuchEventException
import ru.surf.testing.mapper.CandidateInfoMapper
import ru.surf.testing.mapper.TestVariantMapper
import ru.surf.testing.service.CandidateInfoService
import ru.surf.testing.service.TestTemplateService
import ru.surf.testing.service.TestVariantService
import ru.surf.testing.sharedDto.CandidateScoresResponseDto
import java.util.*

@RestController
@RequestMapping(value = ["/test_variant"])
class TestVariantController(
        @Autowired
        private val testTemplateService: TestTemplateService,

        @Autowired
        private val testVariantService: TestVariantService,

        @Autowired
        private val candidateInfoService: CandidateInfoService,

        @Autowired
        private val testVariantMapper: TestVariantMapper,

        @Autowired
        private val candidateInfoMapper: CandidateInfoMapper,
) {

    @PutMapping
    fun create(@RequestBody candidateInfoFullDto: CandidateInfoFullDto): ResponseEntity<TestVariantResponseDto> =
            testVariantService.create(
                    testTemplate=testTemplateService.getByEventId(candidateInfoFullDto.eventId) ?:
                        throw NoSuchEventException(candidateInfoFullDto.eventId),
                    candidate=candidateInfoMapper.toEntity(candidateInfoFullDto)
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
    fun computeCandidateScore(@PathVariable candidateId: UUID): ResponseEntity<CandidateScoreResponseDto> =
            testVariantService.computeCandidateScore(candidateId).let {
                ResponseEntity.ok(CandidateScoreResponseDto(
                        candidateId=candidateId,
                        score=it
                ))
            }

    @GetMapping(value = ["/scores/{eventId}"])
    fun getCandidateScoresByEventId(@PathVariable eventId: UUID): ResponseEntity<CandidateScoresResponseDto> =
            ResponseEntity.ok(
                    CandidateScoresResponseDto(
                            eventId=eventId,
                            scores=candidateInfoService.getAllByEventId(eventId).map { candidateInfo ->
                                CandidateScoreResponseDto(
                                        candidateId=candidateInfo.id,
                                        score=testVariantService.computeCandidateScore(candidateInfo.id)
                                )
                            }
                    )
            )

}