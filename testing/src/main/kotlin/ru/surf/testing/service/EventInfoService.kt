package ru.surf.testing.service

import ru.surf.testing.entity.EventInfo
import ru.surf.testing.entity.TestingPhaseState
import java.util.*

interface EventInfoService {

    fun getById(eventId: UUID): EventInfo

    fun moveToNextTestingPhaseState(eventId: UUID): TestingPhaseState

}