package com.projectAnya.stunthink.domain.use_case.validate

class ValidateNameUseCase {

    fun execute(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Nama tidak boleh kosong"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}