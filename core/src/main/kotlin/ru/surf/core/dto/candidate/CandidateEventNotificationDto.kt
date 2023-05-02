package ru.surf.core.dto.candidate

class CandidateEventNotificationDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val eventsName: Set<String>
) {
}