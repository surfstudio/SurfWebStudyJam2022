package ru.surf.mail.model

enum class Template(
    val html: String
) {
    EVENT_START_NOTIFICATION("event_start_notification.html"),
    ACCEPT_APPLICATION("accept_application.html"),
    TEST("test_link.html"),
    TEST_PASSED("test_passed.html"),
    ACCOUNT_ACTIVATION("account_activation.html"),
    DEFENCE_CREATE_NOTIFICATION("defence_create_notification.html"),
    DEFENCE_CANCEL_NOTIFICATION("defence_cancel_notification.html")
}