package com.example.stunthink.presentation.screen.register

sealed class RegisterFormEvent {
    data class NameChanged(val name: String) : RegisterFormEvent()
    data class EmailChanged(val email: String) : RegisterFormEvent()
    data class PasswordChanged(val password: String) : RegisterFormEvent()
    data class ConfirmationPasswordChanged(val confirmationPassword: String) : RegisterFormEvent()
    data class GenderChanged(val gender: String) : RegisterFormEvent()
    data class DateChanged(val date: String) : RegisterFormEvent()
    data class AddressChanged(val address: String) : RegisterFormEvent()

    object Submit: RegisterFormEvent()
}
