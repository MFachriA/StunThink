package com.example.stunthink.presentation.screen.main.camera

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.domain.common.Resource
import com.example.stunthink.domain.use_case.monitoring.food_detection.UploadFoodPictureUseCase
import com.example.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.example.stunthink.utils.PhotoUriManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val photoUriManager: PhotoUriManager,
    private val getUserTokenUseCase: GetUserTokenUseCase,
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

    private val _userToken = MutableStateFlow<String?>(null)
    val userToken: StateFlow<String?>
        get() = _userToken

    private val _foodId = MutableStateFlow<String?>(null)

    init {
        fetchUserToken()
    }

    private fun fetchUserToken() {
        viewModelScope.launch {
            val token = getUserTokenUseCase.execute().first()
            _userToken.value = token
        }
    }

    fun uploadFoodImage(token: String, image: File) {
        uploadFoodPictureUseCase.invoke(token = token, image = image).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CameraState(food = result.data)
                    if (result.success == true) {
                        validationEventChannel.send(
                            ValidationEvent.Success(id = result.data?.dataGizi?.id ?: "0")
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
                }
                is Resource.Loading -> {
                    _state.value = CameraState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

        sealed class ValidationEvent {
            data class Success(val id: String): ValidationEvent()
            data class Failed(val message: String): ValidationEvent()
        }
}