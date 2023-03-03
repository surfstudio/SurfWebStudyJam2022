package ru.surf.testing.service

import ru.surf.testing.entity.CandidateInfo
import java.util.*

interface CandidateInfoService {

    fun getAllByEventId(eventId: UUID): List<CandidateInfo>

}