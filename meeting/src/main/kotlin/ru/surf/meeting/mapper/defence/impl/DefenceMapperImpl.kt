package ru.surf.meeting.mapper.defence.impl


import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.CalScale
import net.fortuna.ical4j.model.property.Version
import org.springframework.stereotype.Component
import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Trainee
import ru.surf.core.kafkaEvents.CancelDefenceNotificationEvent
import ru.surf.core.kafkaEvents.CreateDefenceNotificationEvent
import ru.surf.core.kafkaEvents.EmailType
import ru.surf.core.kafkaEvents.meeting.CancelDefenceMeetingEvent
import ru.surf.core.kafkaEvents.meeting.CreateDefenceMeetingEvent
import ru.surf.core.kafkaEvents.meeting.MeetingEvent
import ru.surf.meeting.mapper.defence.DefenceMapper
import java.time.ZoneOffset

@Component
class DefenceMapperImpl : DefenceMapper {

    override fun convertCreateDefenceKafkaEventToListNotificationMailEvents(createDefenceEvent: CreateDefenceMeetingEvent):
            List<CreateDefenceNotificationEvent> = createDefenceEvent.candidateParticipants.map {
        transformTraineeToCreateDefenceNotification(
            it,
            createDefenceEvent
        )
    }.toList() + createDefenceEvent.surfParticipants.map {
        transformSurfEmployeeToCreateDefenceNotification(
            it,
            createDefenceEvent
        )
    }.toList()

    override fun convertCancelDefenceKafkaEventToListNotificationMailEvents(
        cancelDefenceMeetingEvent: CancelDefenceMeetingEvent
    ): List<CancelDefenceNotificationEvent> = cancelDefenceMeetingEvent.traineeParticipants.map {
        transformTraineeToCancelDefenceNotification(
            it,
            cancelDefenceMeetingEvent
        )
    }.toList() + cancelDefenceMeetingEvent.surfParticipants.map {
        transformSurfEmployeeToCancelDefenceNotification(
            it,
            cancelDefenceMeetingEvent
        )
    }.toList()

    fun transformTraineeToCreateDefenceNotification(
        trainee: Trainee,
        createDefenceEvent: CreateDefenceMeetingEvent
    ): CreateDefenceNotificationEvent =
        CreateDefenceNotificationEvent(
            emailType = EmailType.DEFENCE_CREATE_NOTIFICATION,
            emailTo = trainee.email,
            subject = "Проведение защиты проекта",
            eventName = createDefenceEvent.eventName,
            firstName = trainee.candidate.firstName,
            lastName = trainee.candidate.lastName,
            zoomLink = createDefenceEvent.zoomLink,
            attachment = listOf(createAttachment(event = createDefenceEvent))
        )

    fun transformTraineeToCancelDefenceNotification(
        trainee: Trainee,
        cancelDefenceMeetingEvent: CancelDefenceMeetingEvent
    ): CancelDefenceNotificationEvent =
        CancelDefenceNotificationEvent(
            emailType = EmailType.DEFENCE_CANCEL_NOTIFICATION,
            emailTo = trainee.email,
            subject = "Отмена защиты проекта",
            eventName = cancelDefenceMeetingEvent.title,
            firstName = trainee.candidate.firstName,
            lastName = trainee.candidate.lastName,
        )

    private fun transformSurfEmployeeToCreateDefenceNotification(
        surfEmployee: SurfEmployee,
        createDefenceEvent: CreateDefenceMeetingEvent
    ): CreateDefenceNotificationEvent =
        CreateDefenceNotificationEvent(
            emailType = EmailType.DEFENCE_CREATE_NOTIFICATION,
            // TODO: 31.03.2023 Убрать позже
            emailTo = "telepuz888@gmail.com",
            subject = "Проведение защиты проекта",
            eventName = createDefenceEvent.eventName,
            // TODO: 06.03.2023 Добавить в таблицу surf_employees first_name last_name
            firstName = surfEmployee.name,
            lastName = surfEmployee.name,
            zoomLink = createDefenceEvent.zoomLink,
            attachment = listOf(createAttachment(event = createDefenceEvent))
        )

    private fun transformSurfEmployeeToCancelDefenceNotification(
        surfEmployee: SurfEmployee,
        cancelDefenceMeetingEvent: CancelDefenceMeetingEvent
    ): CancelDefenceNotificationEvent =
        CancelDefenceNotificationEvent(
            emailType = EmailType.DEFENCE_CANCEL_NOTIFICATION,
            // TODO: 31.03.2023 Убрать позже
            emailTo = "",
            subject = "Отмена защиты проекта",
            eventName = cancelDefenceMeetingEvent.title,
            // TODO: 06.03.2023 Добавить в таблицу surf_employees first_name last_name
            firstName = surfEmployee.name,
            lastName = surfEmployee.name,
        )

    private fun createAttachment(event: MeetingEvent): ByteArray =
        with(event) {
            val startDateTimeInMillis = event.date.toInstant(ZoneOffset.UTC).toEpochMilli()
            val endDateTimeInMillis =
                event.date.plusMinutes(event.duration.toLong()).toInstant(ZoneOffset.UTC).toEpochMilli()
            return Calendar().withDefaults().withComponent(
                VEvent(DateTime(startDateTimeInMillis), DateTime(endDateTimeInMillis), event.description)
            ).withProperty(Version.VERSION_2_0).withProperty(CalScale.GREGORIAN).fluentTarget
                .run {
                    this.toString().toByteArray()
                }
        }

}
