package com.projectAnya.stunthink.domain.use_case.validate

import com.projectAnya.stunthink.utils.DateUtils.isDatePassed


class ValidateDateUseCase {

    fun execute(date: String): ValidationResult {
        if(date.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Pilih tanggal"
            )
        }
        if(isDatePassed(date)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Tanggal tidak boleh lebih dari hari ini"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}