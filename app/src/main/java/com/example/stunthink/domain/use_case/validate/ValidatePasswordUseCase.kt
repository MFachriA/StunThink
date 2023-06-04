package com.example.stunthink.domain.use_case.validate

class ValidatePasswordUseCase {

    fun execute(password: String): ValidationResult {
        if(password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = "Kata sandi harus terdiri dari minimal 6 karakter"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Kata sandi harus mengandung setidaknya satu huruf dan angka"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}