package com.example.stunthink.domain.use_case.validate

class ValidateAddressUseCase {

    fun execute(address: String): ValidationResult {
        if(address.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Alamat tidak boleh kosong"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}