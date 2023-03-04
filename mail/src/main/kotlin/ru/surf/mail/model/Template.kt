package ru.surf.mail.model

enum class Template(
    val html: String
) {
    EVENT_START_NOTIFICATION("event_start_notification.html"),
    ACCEPT_APPLICATION("accept_application.html"),
    TEST("test_link.html"),
    TEST_PASSED("test_passed.html"),
    ACCOUNT_ACTIVATION("account_activation.html"),
}