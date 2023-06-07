package com.example.stunthink.presentation.screen.main.camera

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.stunthink.utils.PhotoUriManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val photoUriManager: PhotoUriManager
): ViewModel() {

    fun getNewSelfieUri() = photoUriManager.buildNewUri()

    private val _selfieUri = mutableStateOf<Uri?>(null)
    val selfieUri
        get() = _selfieUri.value
    fun onSelfieResponse(uri: Uri) {
        _selfieUri.value = uri
    }
}