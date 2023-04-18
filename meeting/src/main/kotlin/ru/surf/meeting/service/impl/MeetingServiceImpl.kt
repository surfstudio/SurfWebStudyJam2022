package ru.surf.meeting.service.impl

import org.springframework.stereotype.Service
import ru.surf.core.kafkaEvents.meeting.CancelDefenceMeetingEvent
import ru.surf.core.kafkaEvents.meeting.CreateDefenceMeetingEvent
import ru.surf.meeting.mapper.defence.DefenceMapper
import ru.surf.meeting.service.KafkaService
import ru.surf.meeting.service.MeetingService
import ru.surf.meeting.service.ZoomIntegrationService

@Service
class MeetingServiceImpl(private val kafkaService: KafkaService,
                         private val defenceMapper: DefenceMapper,
                         private val zoomIntegrationService: ZoomIntegrationService) : MeetingService {

    override fun createMeeting(createDefenceMeetingEvent: CreateDefenceMeetingEvent) =
            defenceMapper.convertCreateDefenceKafkaEventToListNotificationMailEvents(createDefenceMeetingEvent)
                    .forEach { kafkaService.sendCoreEvent(it) }

    override fun cancelMeeting(cancelDefenceMeetingEvent: CancelDefenceMeetingEvent) {
        zoomIntegrationService.deleteZoomMeeting(cancelDefenceMeetingEvent.zoomMeetingId)
        defenceMapper.convertCancelDefenceKafkaEventToListNotificationMailEvents(cancelDefenceMeetingEvent)
                .forEach { kafkaService.sendCoreEvent(it) }
    }

}