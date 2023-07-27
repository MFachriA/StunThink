package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.add

sealed class AddPregnancyFormEvent {
    data class DateChanged(val height: String) : AddPregnancyFormEvent()

    object Submit: AddPregnancyFormEvent()
}
