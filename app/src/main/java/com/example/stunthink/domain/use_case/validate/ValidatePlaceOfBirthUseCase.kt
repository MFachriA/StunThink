package com.example.stunthink.domain.use_case.validate

class ValidatePlaceOfBirthUseCase {

    fun execute(address: String): ValidationResult {
        if(address.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Tempat lahir tidak boleh kosong"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}