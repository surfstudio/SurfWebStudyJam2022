package ru.surf.testing.mapper

import ru.surf.testing.dto.responce.TestInfoResponseDto
import ru.surf.testing.dto.responce.TestVariantResponseDto
import ru.surf.testing.entity.TestVariant

interface TestVariantMapper {

    fun toDto(testVariant: TestVariant): TestVariantResponseDto

    fun toTestInfoDto(testVariant: TestVariant): TestInfoResponseDto

}