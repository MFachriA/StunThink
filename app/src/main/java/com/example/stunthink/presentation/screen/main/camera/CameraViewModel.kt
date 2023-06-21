package com.example.stunthink.presentation.screen.main.camera

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.data.remote.dto.nutrition.FoodDto
import com.example.stunthink.domain.common.Resource
import com.example.stunthink.domain.use_case.monitoring.food_detection.UploadFoodPictureUseCase
import com.example.stunthink.utils.PhotoUriManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val photoUriManager: PhotoUriManager,
    private val uploadFoodPictureUseCase: UploadFoodPictureUseCase
): ViewModel() {

    fun getNewSelfieUri() = photoUriManager.buildNewUri()

    private val _selfieUri = mutableStateOf<Uri?>(null)
    val selfieUri
        get() = _selfieUri.value
    fun onSelfieResponse(uri: Uri) {
        _selfieUri.value = uri
    }

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _state = mutableStateOf(CameraState())
    val state: State<CameraState> = _state

    fun uploadFoodImage(token: String, image: File) {
        uploadFoodPictureUseCase.invoke(token = token, image = image).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CameraState(food = result.data)
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
                    _state.value = CameraState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    validationEventChannel.send(
                        ValidationEvent.Failed(message = state.value.error)
                    )
                }
                is Resource.Loading -> {
                    _state.value = CameraState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

        sealed class ValidationEvent {
            data class Success(val food: FoodDto?): ValidationEvent()
            data class Failed(val message: String): ValidationEvent()
        }
}