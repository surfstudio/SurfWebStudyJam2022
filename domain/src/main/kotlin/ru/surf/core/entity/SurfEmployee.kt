package ru.surf.core.entity

import jakarta.persistence.*

@Table(name = "surf_employees")
@Entity
class SurfEmployee(

        @Column(name = "name", nullable = false)
        var name: String = "",

        ) : Account() {

    override fun toString(): String {
        return "SurfEmployee(name='$name') ${super.toString()}"
    }

}