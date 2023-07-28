package com.projectAnya.stunthink.presentation.screen.main.profile.edit

sealed class EditProfileFormEvent {
    data class NameChanged(val name: String) : EditProfileFormEvent()
    data class GenderChanged(val gender: String) : EditProfileFormEvent()
    data class DateChanged(val date: String) : EditProfileFormEvent()
    data class AddressChanged(val address: String) : EditProfileFormEvent()

    object Submit: EditProfileFormEvent()
}
