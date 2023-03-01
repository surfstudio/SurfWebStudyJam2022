package ru.surf.auth.entity

import jakarta.persistence.*


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Identity(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        open var id: Long = 0
)
