package com.projectAnya.stunthink.presentation.screen.monitoring.child.stuntingdetection

sealed class StuntingDetectionFormEvent {
    data class HeightChanged(val height: Int) : StuntingDetectionFormEvent()
    data class SupineChanged(val isSupine: Boolean) : StuntingDetectionFormEvent()

    object Submit: StuntingDetectionFormEvent()
}
