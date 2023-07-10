package com.projectAnya.stunthink.presentation.screen.monitoring.child.main

import androidx.compose.foundation.gestures.DraggableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildMonitoringMainViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
): ViewModel() {

    private val _childIdState = MutableStateFlow<String?>(null)
    val childIdState: StateFlow<String?> = _childIdState.asStateFlow()

    private val _childNameState = MutableStateFlow<String?>(null)
    val childNameState: StateFlow<String?> = _childNameState.asStateFlow()

    private val _userTokenState = MutableStateFlow<String?>(null)
    val userTokenState: StateFlow<String?> = _userTokenState.asStateFlow()

    private val _dateState: MutableLiveData<String?> = MutableLiveData(null)
    val dateState: LiveData<String?> = _dateState

    init {
        fetchUserToken()
    }

    private fun fetchUserToken() {
        viewModelScope.launch {
            getUserTokenUseCase.execute().collect { token ->
                _userTokenState.value = token
            }
        }
    }

    fun setChildId(id: String?) {
        _childIdState.value = id
    }

    fun setChildName(name: String?) {
        _childNameState.value = name
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

    fun updateDate(date: String?) {
        _dateState.value = date
    }
}