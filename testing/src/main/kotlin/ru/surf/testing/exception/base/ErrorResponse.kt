package ru.surf.testing.exception.base


@Suppress("unused")
class ErrorResponse(
        val error: String?,
        val description: String,
        val params: Map<String, *> = mapOf<String, Any>()
)
