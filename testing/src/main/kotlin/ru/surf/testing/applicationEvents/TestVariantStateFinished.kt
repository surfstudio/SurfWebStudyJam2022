package ru.surf.testing.applicationEvents

import org.springframework.context.ApplicationEvent
import ru.surf.testing.entity.TestVariant

class TestVariantStateFinished(
        sender: Any,
        val testVariant: TestVariant,
) : ApplicationEvent(sender)