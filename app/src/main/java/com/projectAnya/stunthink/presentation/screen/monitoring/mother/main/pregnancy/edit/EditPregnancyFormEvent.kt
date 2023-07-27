package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.edit

sealed class EditPregnancyFormEvent {
    data class DateChanged(val height: String) : EditPregnancyFormEvent()

    object Submit: EditPregnancyFormEvent()
}
