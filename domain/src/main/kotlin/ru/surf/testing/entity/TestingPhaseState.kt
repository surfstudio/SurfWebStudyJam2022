package ru.surf.testing.entity

enum class TestingPhaseState {

    PENDING {
        override val nextPhase: TestingPhaseState
            get() = ACTIVE
    },
    ACTIVE {
        override val nextPhase: TestingPhaseState
            get() = COMPLETE
    },
    COMPLETE {
        override val nextPhase: TestingPhaseState?
            get() = null
    };

    abstract val nextPhase: TestingPhaseState?

}