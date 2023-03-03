package ru.surf.testing.service

import ru.surf.testing.entity.TestVariant

interface TestVariantFinalizerService {

    fun registerStartedTest(testVariant: TestVariant)

    fun finalizeTest(testVariant: TestVariant)

}