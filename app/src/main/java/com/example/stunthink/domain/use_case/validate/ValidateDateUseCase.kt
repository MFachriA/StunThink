package com.example.stunthink.domain.use_case.validate

import com.example.stunthink.utils.isDatePassed

class ValidateDateUseCase {

    fun execute(date: String): ValidationResult {
        if(date.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Pilih tanggal lahir"
            )
        }
        if(isDatePassed(date)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Tanggal lahir tidak boleh lebih dari hari ini"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}