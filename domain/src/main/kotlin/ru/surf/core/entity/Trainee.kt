package ru.surf.core.entity

import javax.persistence.*

@Table(name = "trainees")
@Entity
class Trainee(

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "candidate_id", nullable = false, unique = true)
        val candidate: Candidate = Candidate(),

        @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
        @JoinColumn(name = "team_id", nullable = true)
        val team: Team? = null,

        ) : Account(email = candidate.email) {

        override fun toString(): String {
                return "Trainee(candidate=$candidate, team=$team) ${super.toString()}"
        }

}