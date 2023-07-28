package com.projectAnya.stunthink.domain.use_case.validate

class ValidateFieldUseCase {
    fun execute(value: String): ValidationResult {
        if (value.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Bagian ini harus diisi"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}