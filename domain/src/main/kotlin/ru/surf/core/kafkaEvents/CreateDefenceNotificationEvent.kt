package ru.surf.core.kafkaEvents

data class CreateDefenceNotificationEvent(
    override val emailType: EmailType = EmailType.DEFENCE_CREATE_NOTIFICATION,
    override val emailTo: String,
    override val subject: String,
    val eventName: String,
    val firstName: String,
    val lastName: String,
    var zoomLink: String,
    @JvmField var attachment: List<ByteArray>
) : MailEvent {
    override fun getAttachment(): List<ByteArray> = attachment

    override fun params(): Map<String, *> {
        return mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "eventName" to eventName,
            "zoomLink" to zoomLink
        )
    }
}