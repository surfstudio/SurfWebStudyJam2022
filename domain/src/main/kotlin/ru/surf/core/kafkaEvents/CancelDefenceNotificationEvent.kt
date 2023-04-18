package ru.surf.core.kafkaEvents

data class CancelDefenceNotificationEvent(
        override val emailType: EmailType = EmailType.DEFENCE_CANCEL_NOTIFICATION,
        override val emailTo: String,
        override val subject: String,
        val eventName: String,
        val firstName: String,
        val lastName: String,
) : MailEvent {
    override fun getAttachment(): List<ByteArray> {
        return super.getAttachment()
    }
    override fun params(): Map<String, *> {
        return mapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "eventName" to eventName,
        )
    }
}