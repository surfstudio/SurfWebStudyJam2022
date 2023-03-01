package ru.surf.testing.applicationEvents

import org.springframework.context.ApplicationEvent
import ru.surf.testing.entity.TestTemplate

class TestTemplateReplaced(
        sender: Any,
        val template: TestTemplate,
) : ApplicationEvent(sender)