package ru.surf.core.util

enum class KeywordsPath(val path: String) {
    JAVA_BACKEND_KEYWORDS_PATH("keywords/JAVA_BACKEND_KEYWORDS.txt")
}

fun readFileStrings(path: String) =
    KeywordsPath::class.java.classLoader.
    getResource(path)?.
    readText(Charsets.UTF_8)?.
    filterNot { it.isWhitespace() }?.split(",")
            ?: listOf()