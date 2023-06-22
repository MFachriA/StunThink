package com.project.stunthink.presentation.screen.monitoring.child.main

import androidx.compose.foundation.gestures.DraggableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.stunthink.domain.use_case.user.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildMonitoringMainViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
): ViewModel() {

    private val _userToken = MutableStateFlow<String?>(null)
    val userToken: StateFlow<String?>
        get() = _userToken

    init {
        fetchUserToken()
    }

    private fun fetchUserToken() {
        viewModelScope.launch {
            val token = getUserTokenUseCase.execute().first()
            _userToken.value = token
        }
    }

    private val _tabIndex: MutableLiveData<Int> = MutableLiveData(0)
    val tabIndex: LiveData<Int> = _tabIndex
    val tabs = listOf("Nutrisi", "Stunting")

    var isSwipeToTheLeft: Boolean = false
    private val draggableState = DraggableState { delta ->
        isSwipeToTheLeft= delta > 0
    }

    private val _dragState = MutableLiveData(draggableState)
    val dragState: LiveData<DraggableState> = _dragState

    fun updateTabIndexBasedOnSwipe() {
        _tabIndex.value = when (isSwipeToTheLeft) {
            true -> Math.floorMod(_tabIndex.value!!.plus(1), tabs.size)
            false -> Math.floorMod(_tabIndex.value!!.minus(1), tabs.size)
        }
    }

    fun updateTabIndex(i: Int) {
        _tabIndex.value = i
    }

}