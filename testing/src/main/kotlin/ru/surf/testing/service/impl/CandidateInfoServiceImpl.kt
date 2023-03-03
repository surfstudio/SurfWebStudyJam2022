package ru.surf.testing.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.surf.testing.entity.CandidateInfo
import ru.surf.testing.repository.CandidateInfoRepository
import ru.surf.testing.service.CandidateInfoService
import java.util.*

@Service
class CandidateInfoServiceImpl(

        @Autowired
        private val candidateInfoRepository: CandidateInfoRepository,

) : CandidateInfoService {

    override fun getAllByEventId(eventId: UUID): List<CandidateInfo> =
            candidateInfoRepository.
            findAllByEventInfoId(eventId)

}