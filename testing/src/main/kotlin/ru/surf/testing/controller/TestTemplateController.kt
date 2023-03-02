package ru.surf.testing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.surf.testing.dto.request.TestTemplatePutRequestDto
import ru.surf.testing.dto.responce.TestTemplateResponseDto
import ru.surf.testing.mapper.TestTemplateMapper
import ru.surf.testing.service.TestTemplateService
import java.util.*

@RestController
@RequestMapping(value = ["/test_template"])
class TestTemplateController(
        @Autowired
        private val testTemplateService: TestTemplateService,

        @Autowired
        private val testTemplateMapper: TestTemplateMapper
) {

    @PutMapping(value = ["/"])
    fun create(@RequestBody testTemplatePutRequestDto: TestTemplatePutRequestDto): ResponseEntity<TestTemplateResponseDto> =
            ResponseEntity(
                    testTemplateMapper.toDto(
                        testTemplateService.create(
                                testTemplateMapper.toEntity(testTemplatePutRequestDto)
                        )
                    ),
                    HttpStatus.CREATED
            )

    @GetMapping(value = ["/event/{id}"])
    fun getByEventId(@PathVariable id: UUID): ResponseEntity<TestTemplateResponseDto> =
            testTemplateService.getByEventId(id)?.let {
                ResponseEntity.ok(testTemplateMapper.toDto(it))
            } ?: ResponseEntity(HttpStatus.NOT_FOUND)

}