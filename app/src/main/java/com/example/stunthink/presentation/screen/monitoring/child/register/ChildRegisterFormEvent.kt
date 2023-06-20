package com.example.stunthink.presentation.screen.monitoring.child.register

sealed class ChildRegisterFormEvent {
    data class NameChanged(val name: String) : ChildRegisterFormEvent()
    data class GenderChanged(val gender: String) : ChildRegisterFormEvent()
    data class DateChanged(val date: String) : ChildRegisterFormEvent()
    data class AddressChanged(val address: String) : ChildRegisterFormEvent()

    object Submit: ChildRegisterFormEvent()
}
