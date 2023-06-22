package com.project.stunthink.domain.use_case.validate

class ValidateConfirmationPasswordUseCase {

    fun execute(password: String, confirmationPassword: String): ValidationResult {
        if(password != confirmationPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "Kata sandi tidak cocok"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}