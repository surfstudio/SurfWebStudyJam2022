package ru.surf.core.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class CustomEmailValidation :
      ConstraintValidator<EmailConstraint, Any?> {

    private lateinit var constraint: EmailConstraint

    override fun initialize(constraint: EmailConstraint) {
        this.constraint = constraint
    }

    override fun isValid(field: Any?,
                         cxt: ConstraintValidatorContext): Boolean {
        return field is String &&
               field.matches(
                       Regex(
                               constraint.regexp,
                               constraint.flags.toSet()
                       )
               )
    }

}
