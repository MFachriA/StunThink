package com.projectAnya.stunthink.domain.use_case.validate

class ValidateDropDownUseCase {
    fun execute(value: String): ValidationResult {
        if (value.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Bagian ini harus dipilih"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}