package ru.surf.testing.applicationEvents

import org.springframework.context.ApplicationEvent
import ru.surf.testing.entity.EventInfo

class TestingPhaseStateChanged(
        sender: Any,
        val eventInfo: EventInfo,
) : ApplicationEvent(sender)