@file:Suppress("unused")

package ru.surf.core.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [CustomEmailValidation::class])
@Target(allowedTargets = [AnnotationTarget.FIELD])
@Retention(value = AnnotationRetention.RUNTIME)
annotation class EmailConstraint(
        val message: String = "Email address seems not valid",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [],

        val regexp: String = ".*",
        val flags: Array<RegexOption> = [],
)