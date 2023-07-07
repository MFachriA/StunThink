package com.projectAnya.stunthink.presentation.screen.monitoring.child.camera

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.data.remote.dto.height.HeightDto
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.height_detection.UploadHeightPictureUseCase
import com.projectAnya.stunthink.utils.PhotoUriManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StuntingCameraViewModel @Inject constructor(
    private val photoUriManager: PhotoUriManager,
    private val uploadHeightPictureUseCase: UploadHeightPictureUseCase
): ViewModel() {

    fun getNewHeightUri() = photoUriManager.buildNewUri()

    private val _heightUri = mutableStateOf<Uri?>(null)
    val heightUri
        get() = _heightUri.value
    fun onHeightResponse(uri: Uri) {
        _heightUri.value = uri
    }

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _state = mutableStateOf(StuntingCameraState())
    val state: State<StuntingCameraState> = _state

    fun uploadHeightImage(image: File) {
        uploadHeightPictureUseCase.invoke(image = image).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = StuntingCameraState(food = result.data)
                    if (result.success == true) {
                        validationEventChannel.send(
                            ValidationEvent.Success(result.data)
                        )
                    } else if (result.success == false) {
                        validationEventChannel.send(
                            ValidationEvent.Failed(message = result.message ?: "Failed")
                        )
                    }
                }
                is Resource.Error -> {
                    _state.value = StuntingCameraState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    validationEventChannel.send(
                        ValidationEvent.Failed(message = state.value.error)
                    )
                }
                is Resource.Loading -> {
                    _state.value = StuntingCameraState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

        sealed class ValidationEvent {
            data class Success(val height: HeightDto?): ValidationEvent()
            data class Failed(val message: String): ValidationEvent()
        }
}