package ru.surf.core.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import ru.surf.core.entity.Account
import ru.surf.core.entity.SurfEmployee

class SurfEmployeeRoleValidation :
      ConstraintValidator<SurfEmployeeRoleConstraint, SurfEmployee> {

    override fun initialize(constraint: SurfEmployeeRoleConstraint) {
    }

    override fun isValid(surfEmployee: SurfEmployee,
                         cxt: ConstraintValidatorContext): Boolean {
        return surfEmployee.role != Account.Role.TRAINEE
    }

}
