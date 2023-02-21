package ru.surf.core.util

import org.springframework.core.io.ClassPathResource

enum class KeywordsPath(val path: String) {
    JAVA_BACKEND_KEYWORDS_PATH("keywords/JAVA_BACKEND_KEYWORDS.txt")
}

fun readFileStrings(path: String) =
    ClassPathResource(path).file.readText(Charsets.UTF_8).filterNot { it.isWhitespace() }.split(",")