package ru.surf.externalfiles.enum

enum class FileExtension(val extensions: List<String>) {

    IMAGE(listOf("IMAGE/JPEG", "IMAGE/PNG", "IMAGE/GIF")),
    DOCUMENT(
        listOf(
            "TEXT/PLAIN",
            "APPLICATION/VND.OPENXMLFORMATS-OFFICEDOCUMENT.WORDPROCESSINGML.DOCUMENT",
            "APPLICATION/PDF",
            "APPLICATION/VND.OPENXMLFORMATS-OFFICEDOCUMENT.SPREADSHEETML.SHEET"
        )
    )
}
