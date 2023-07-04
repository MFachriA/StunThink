package com.projectAnya.stunthink.domain.use_case.validate

class ValidateHeightUseCase {
    fun execute(height: String): ValidationResult {
        if (height.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Tinggi tidak boleh kosong"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}