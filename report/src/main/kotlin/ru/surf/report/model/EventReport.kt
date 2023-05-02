package ru.surf.report.model

import ru.surf.core.entity.EventState

data class EventReport(
    var eventDescription: String = "",
    var eventStates: Set<EventState> = mutableSetOf(),
    var peopleAmountByStates: Map<Int, Int> = mutableMapOf(),
    var testResults: Map<Int, Int> = mutableMapOf(),
    var teamResults: Map<String, Double> = mutableMapOf()
)