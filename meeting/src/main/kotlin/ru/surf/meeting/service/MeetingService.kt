package ru.surf.meeting.service

import ru.surf.core.kafkaEvents.meeting.CancelDefenceMeetingEvent
import ru.surf.core.kafkaEvents.meeting.CreateDefenceMeetingEvent

interface MeetingService {

    fun createMeeting(createDefenceMeetingEvent: CreateDefenceMeetingEvent)

    fun cancelMeeting(cancelDefenceMeetingEvent: CancelDefenceMeetingEvent)

}