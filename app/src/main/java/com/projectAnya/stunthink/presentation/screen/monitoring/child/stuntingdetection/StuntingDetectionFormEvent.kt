package com.projectAnya.stunthink.presentation.screen.monitoring.child.stuntingdetection

sealed class StuntingDetectionFormEvent {
    data class HeightChanged(val height: String) : StuntingDetectionFormEvent()
    data class SupineChanged(val isSupine: String) : StuntingDetectionFormEvent()

    object Submit: StuntingDetectionFormEvent()
}
