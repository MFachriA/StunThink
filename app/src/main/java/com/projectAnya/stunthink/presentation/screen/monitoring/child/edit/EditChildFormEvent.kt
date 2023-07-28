package com.projectAnya.stunthink.presentation.screen.monitoring.child.edit

sealed class EditChildFormEvent {
    data class NameChanged(val name: String) : EditChildFormEvent()
    data class GenderChanged(val gender: String) : EditChildFormEvent()
    data class DateChanged(val date: String) : EditChildFormEvent()
    data class AddressChanged(val address: String) : EditChildFormEvent()

    object Submit: EditChildFormEvent()
}
