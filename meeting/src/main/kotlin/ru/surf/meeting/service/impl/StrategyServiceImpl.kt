package ru.surf.meeting.service.impl

import org.springframework.stereotype.Service
import ru.surf.core.kafkaEvents.meeting.CancelDefenceMeetingEvent
import ru.surf.core.kafkaEvents.meeting.CreateDefenceMeetingEvent
import ru.surf.meeting.exception.UnsupportedEventException
import ru.surf.meeting.service.MeetingService
import ru.surf.meeting.service.StrategyService

@Service
class StrategyServiceImpl(private val meetingService: MeetingService) : StrategyService {

    override fun chooseStrategy(event: Any): Any =
        when (event) {
            is CreateDefenceMeetingEvent -> meetingService.createMeeting(event)
            is CancelDefenceMeetingEvent -> meetingService.cancelMeeting(event)
            else -> throw UnsupportedEventException(event::class.java.name)
        }

}