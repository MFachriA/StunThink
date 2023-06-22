package com.projectAnya.stunthink.domain.use_case.validate

import android.util.Patterns

class ValidateEmailUseCase {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email tidak boleh kosong"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email tidak valid"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}