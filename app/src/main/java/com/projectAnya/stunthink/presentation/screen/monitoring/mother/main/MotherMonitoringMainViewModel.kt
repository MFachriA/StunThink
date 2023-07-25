package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.mother.GetMotherNutritionUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.nutrition.GetNutritionStandardUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.nutrition.GetNutritionStatusUseCase
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition.ChildNutritionListState
import com.projectAnya.stunthink.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MotherMonitoringMainViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getNutritionStandardUseCase: GetNutritionStandardUseCase,
    private val getNutritionStatusUseCase: GetNutritionStatusUseCase,
    private val getMotherNutritionUseCase: GetMotherNutritionUseCase

): ViewModel() {
    private val _userTokenState = MutableStateFlow<String?>(null)
    val userTokenState: StateFlow<String?> = _userTokenState.asStateFlow()

    private val _dateState: MutableLiveData<String?> = MutableLiveData(null)
    val dateState: LiveData<String?> = _dateState

    private val _nutritionStatusState = mutableStateOf(ChildNutritionListState())
    val nutritionStatusState: State<ChildNutritionListState> = _nutritionStatusState

    private val _nutritionStandardState = mutableStateOf(ChildNutritionListState())
    val nutritionStandardState: State<ChildNutritionListState> = _nutritionStandardState

    private val _nutritionListState = mutableStateOf(ChildNutritionListState())
    val nutritionListState: State<ChildNutritionListState> = _nutritionListState

    private val _tabIndex: MutableLiveData<Int> = MutableLiveData(0)
    val tabIndex: LiveData<Int> = _tabIndex
    val tabs = listOf("Nutrisi", "Kehamilan")

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

    fun updateDate(date: String?) {
        _dateState.value = date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNutritionStatus(
        token: String,
        date: String?
    ) {
        getNutritionStatusUseCase.invoke(
            token = token,
            startDate = DateUtils.getStartOfDay(date),
            endDate = DateUtils.getEndOfDay(date)
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _nutritionStatusState.value = ChildNutritionListState(
                        nutritionStatus = result.data
                    )
                }
                is Resource.Error -> {
                    _nutritionStatusState.value = ChildNutritionListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _nutritionStatusState.value = ChildNutritionListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getNutritionStandard(token: String) {
        getNutritionStandardUseCase(token = token).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _nutritionStandardState.value = ChildNutritionListState(
                        nutritionStandard = result.data
                    )
                }
                is Resource.Error -> {
                    _nutritionStandardState.value = ChildNutritionListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _nutritionStandardState.value = ChildNutritionListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getNutritions(
        token: String,
        date: String?
    ) {
        getMotherNutritionUseCase(
            token = token,
            startDate = DateUtils.getStartOfDay(date),
            endDate = DateUtils.getEndOfDay(date)
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _nutritionListState.value = ChildNutritionListState(nutritions = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _nutritionListState.value = ChildNutritionListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _nutritionListState.value = ChildNutritionListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}