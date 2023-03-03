package ru.surf.testing.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import ru.surf.testing.applicationEvents.TestVariantStateFinished
import ru.surf.testing.entity.TestVariant
import ru.surf.testing.service.TestVariantFinalizerService
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Delayed

@Service
class TestVariantFinalizerServiceImpl(

        @Autowired
        private val taskScheduler: TaskScheduler,

        @Autowired
        private val applicationEventPublisher: ApplicationEventPublisher,

        private val startedTests: ConcurrentHashMap<TestVariant, Delayed> = ConcurrentHashMap(),

) : TestVariantFinalizerService {

    override fun registerStartedTest(testVariant: TestVariant) {
        startedTests[testVariant] = taskScheduler.schedule(
                { finalizeTest(testVariant) },
                testVariant.finishingAt!!.toInstant()
        )
    }

    override fun finalizeTest(testVariant: TestVariant) {
        startedTests.remove(testVariant)?.let {
            applicationEventPublisher.publishEvent(TestVariantStateFinished(
                    sender=this,
                    testVariant=testVariant
            ))
        }
    }

}