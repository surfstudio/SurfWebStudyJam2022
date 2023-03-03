package ru.surf.testing.mapper

import ru.surf.testing.dto.request.TestTemplatePutRequestDto
import ru.surf.testing.dto.responce.TestTemplateResponseDto
import ru.surf.testing.entity.TestTemplate

interface TestTemplateMapper {

    fun toEntity(testTemplatePutRequestDto: TestTemplatePutRequestDto): TestTemplate

    fun toDto(testTemplate: TestTemplate): TestTemplateResponseDto

}