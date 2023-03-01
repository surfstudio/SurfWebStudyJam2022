package ru.surf.testing.service

import ru.surf.testing.entity.TestTemplate
import java.util.*

interface TestTemplateService {

    fun create(testTemplate: TestTemplate): TestTemplate

    fun getByEventId(eventId: UUID): TestTemplate?

}