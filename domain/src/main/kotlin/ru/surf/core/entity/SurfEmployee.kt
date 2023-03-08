package ru.surf.core.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import ru.surf.core.validation.SurfEmployeeRoleConstraint
import java.time.ZonedDateTime

@Table(name = "surf_employees")
@Entity
@SurfEmployeeRoleConstraint
class SurfEmployee(

        email: String = "",

        role: Role = Role.SURF_EMPLOYEE,

        @Column(name = "name", nullable = false)
        var name: String = "",

        @Column(name = "activated_at", nullable = true)
        var activatedAt: ZonedDateTime? = null,

) : Account(email = email, role = role) {

    override fun toString(): String {
        return "SurfEmployee(" +
                "name='$name', " +
                "activatedAt='$activatedAt'" +
                ") ${super.toString()}"
    }

}