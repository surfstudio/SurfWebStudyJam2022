package ru.surf.testing.entity

import jakarta.persistence.*
import jakarta.persistence.CascadeType
import jakarta.persistence.OrderBy
import jakarta.persistence.Table
import org.hibernate.annotations.*
import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*

@Table(name = "test_variants")
@Entity
class TestVariant(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "candidate_id", referencedColumnName = "id", nullable = false, unique = true)
        val candidateInfo: CandidateInfo = CandidateInfo(),

        @Column(name = "started_at", nullable = true)
        var startedAt: ZonedDateTime? = null,

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, optional = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "test_template_id", referencedColumnName = "id", nullable = false)
        val testTemplate: TestTemplate = TestTemplate(),

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = "test_variant_id", nullable = false)
        @OrderBy(value = "questionOrder")
        @BatchSize(size = 10)
        val questionVariants: Set<QuestionVariant> = mutableSetOf(),

        ) : UUIDBasedEntity(id) {

        val finishingAt
                get() = startedAt?.plusSeconds(testTemplate.maxAcceptableDurationSec)

        val state
                get() = TestState.getTestVariantState(this)

        val currentQuestion
                get() = when (state) {
                        TestState.STARTED -> questionVariants.firstOrNull { !it.answered }
                        else -> null
                }

        val totalWeight
                get() = questionVariants.map { it.question }.sumOf { it.weight }

        override fun toString(): String {
                return "TestVariant(" +
                        "id=$id, " +
                        "startedAt=$startedAt)"
        }

        enum class TestState {
                NOT_STARTED,
                STARTED,
                FINISHED;

                companion object {
                        fun getTestVariantState(testVariant: TestVariant): TestState = testVariant.run {
                            when (finishingAt?.isAfter(ZonedDateTime.now())) {
                                    null -> NOT_STARTED

                                    true -> when (questionVariants.any { !it.answered }) {
                                            true -> STARTED
                                            else -> FINISHED
                                    }

                                    false -> FINISHED
                            }
                        }
                }
        }

}