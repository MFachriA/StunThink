package com.example.stunthink.presentation.screen.main.camera

import androidx.lifecycle.ViewModel
import com.example.stunthink.utils.PhotoUriManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val photoUriManager: PhotoUriManager
): ViewModel() {

    fun getNewSelfieUri() = photoUriManager.buildNewUri()
}