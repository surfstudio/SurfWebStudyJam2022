package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Table(name = "test_variants")
@Entity
class TestVariant(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "candidate_id", columnDefinition = "uuid", nullable = false, unique = true)
        val candidateId: UUID = UUID.randomUUID(),

        @Column(name = "started_at", nullable = false)
        var startedAt: ZonedDateTime = ZonedDateTime.now(),

        @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "test_template_id")
        val testTemplate: TestTemplate = TestTemplate(),

        @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "test_variant_id")
        val questions: Set<QuestionVariant> = setOf()

) : UUIDBasedEntity(id) {

        override fun toString(): String {
                return "TestVariant(" +
                        "id=$id, " +
                        "candidateId=$candidateId, " +
                        "startedAt=$startedAt, " +
                        "testTemplate=$testTemplate, " +
                        "questions=$questions)"
        }
}