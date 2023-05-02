package ru.surf.core.dto.card

data class ProjectCardResponseDto(
    var eventName: String,
    val title: String,
    val githubLink: String,
    val miroLink: String,
    val trelloLink: String,
    val googleDriveStorageLink: String,
    val usefulDocumentationLink: String,
    val projectNote: String,
    val version: Long,
)

