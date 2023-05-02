@file:Suppress("unused")

package ru.surf.core.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [SurfEmployeeRoleValidation::class])
@Target(allowedTargets = [AnnotationTarget.CLASS])
@Retention(value = AnnotationRetention.RUNTIME)
annotation class SurfEmployeeRoleConstraint(
        val message: String = "SurfEmployee role cannot be TRAINEE",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)