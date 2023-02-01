package ru.surf.auth.entity

import java.util.*
import javax.persistence.*


@Entity
class Credentials(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "uuid")
        var credentialsId: UUID = UUID.randomUUID(),

        @OneToOne
        var identity: Identity? = null
)