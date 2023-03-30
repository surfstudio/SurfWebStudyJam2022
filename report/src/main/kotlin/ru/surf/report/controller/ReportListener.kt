package ru.surf.report.controller

import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.EndingEvent
import ru.surf.report.service.ReportService
import ru.surf.report.service.ReportWrapper


@Component
@KafkaListener(topics = ["core-topics"])
class ReportListener(
    private val reportService: ReportService,
    private val reportWrapper: ReportWrapper
) {

    @KafkaHandler
    @RetryableTopic
    fun onEndingEvent(@Payload value: EndingEvent) {
        val report = reportWrapper.wrap(
            reportService.getReport(value.eventId)
        )

        reportService.saveReport(report, value.eventId)
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit
}