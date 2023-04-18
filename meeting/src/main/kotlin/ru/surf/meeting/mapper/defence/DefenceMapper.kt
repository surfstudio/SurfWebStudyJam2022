package ru.surf.meeting.mapper.defence

import ru.surf.core.kafkaEvents.CancelDefenceNotificationEvent
import ru.surf.core.kafkaEvents.CreateDefenceNotificationEvent
import ru.surf.core.kafkaEvents.meeting.CancelDefenceMeetingEvent
import ru.surf.core.kafkaEvents.meeting.CreateDefenceMeetingEvent

interface DefenceMapper {

    fun convertCreateDefenceKafkaEventToListNotificationMailEvents(createDefenceEvent: CreateDefenceMeetingEvent):
            List<CreateDefenceNotificationEvent>

    fun convertCancelDefenceKafkaEventToListNotificationMailEvents(cancelDefenceMeetingEvent: CancelDefenceMeetingEvent):
            List<CancelDefenceNotificationEvent>

}