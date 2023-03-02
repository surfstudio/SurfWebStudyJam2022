package ru.surf.testing.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.surf.testing.applicationEvents.TestingPhaseStateChanged
import ru.surf.testing.entity.EventInfo
import ru.surf.testing.entity.TestingPhaseState
import ru.surf.testing.exception.NoSuchEventException
import ru.surf.testing.exception.TestingPhaseCompleteException
import ru.surf.testing.repository.EventInfoRepository
import ru.surf.testing.service.EventInfoService
import java.util.*

@Service
class EventInfoServiceImpl(

        @Autowired
        private val eventInfoRepository: EventInfoRepository,

        @Autowired
        private val applicationEventPublisher: ApplicationEventPublisher,

) : EventInfoService {

    override fun getById(eventId: UUID): EventInfo = eventInfoRepository.findByIdOrNull(eventId) ?:
        throw NoSuchEventException(eventId)

    override fun moveToNextTestingPhaseState(eventId: UUID): TestingPhaseState = getById(eventId).run {
        testingPhaseState = testingPhaseState.nextPhase ?: throw TestingPhaseCompleteException(eventId)
        eventInfoRepository.save(this).also {
            applicationEventPublisher.publishEvent(
                    TestingPhaseStateChanged(
                            sender=this,
                            eventInfo=it
                    )
            )
        }.testingPhaseState
    }

}