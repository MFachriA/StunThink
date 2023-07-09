package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.child.GetChildNutritionUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.nutrition.GetNutritionStandardUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.nutrition.GetNutritionStatusUseCase
import com.projectAnya.stunthink.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChildNutritionViewModel @Inject constructor(
    private val getNutritionStandardUseCase: GetNutritionStandardUseCase,
    private val getNutritionStatusUseCase: GetNutritionStatusUseCase,
    private val getChildNutritionUseCase: GetChildNutritionUseCase
): ViewModel() {

    private val _nutritionStatusState = mutableStateOf(ChildNutritionListState())
    val nutritionStatusState: State<ChildNutritionListState> = _nutritionStatusState

    private val _nutritionStandardState = mutableStateOf(ChildNutritionListState())
    val nutritionStandardState: State<ChildNutritionListState> = _nutritionStandardState

    private val _nutritionListState = mutableStateOf(ChildNutritionListState())
    val nutritionListState: State<ChildNutritionListState> = _nutritionListState

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNutritionStatus(
        token: String,
        id: String,
        startDate: String? = null,
        endDate: String? = null
    ) {
        getNutritionStatusUseCase.invoke(
            token = token,
            startDate = DateUtils.getStartOfDay(startDate),
            endDate = DateUtils.getEndOfDay(endDate),
            isChild = true,
            childId = id
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

    fun getNutritionStandard(
        token: String,
        id: String
    ) {
        getNutritionStandardUseCase(
            token = token,
            id = id
        ).onEach { result ->
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

    fun getNutritions(token: String, id: String) {
        getChildNutritionUseCase(token = token, id = id).onEach { result ->
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