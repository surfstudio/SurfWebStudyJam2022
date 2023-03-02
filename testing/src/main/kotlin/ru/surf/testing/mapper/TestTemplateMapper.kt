package ru.surf.testing.mapper

import ru.surf.testing.dto.responce.TestTemplateResponseDto
import ru.surf.testing.entity.TestTemplate

interface TestTemplateMapper {
    fun toDto(testTemplate: TestTemplate): TestTemplateResponseDto
}