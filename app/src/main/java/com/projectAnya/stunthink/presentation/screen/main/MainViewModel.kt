package com.projectAnya.stunthink.presentation.screen.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.data.remote.dto.education.EducationDto
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.education.GetEducationListUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.nutrition.GetNutritionStandardUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.nutrition.GetNutritionStatusUseCase
import com.projectAnya.stunthink.domain.use_case.user.DeleteUserTokenUseCase
import com.projectAnya.stunthink.domain.use_case.user.GetUserDetailUseCase
import com.projectAnya.stunthink.presentation.screen.main.education.EducationState
import com.projectAnya.stunthink.presentation.screen.main.profile.ProfileState
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition.ChildNutritionListState
import com.projectAnya.stunthink.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getEducationListUseCase: GetEducationListUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val getNutritionStandardUseCase: GetNutritionStandardUseCase,
    private val getNutritionStatusUseCase: GetNutritionStatusUseCase,
    private val deleteUserTokenUseCase: DeleteUserTokenUseCase
): ViewModel() {

    private val _selectedMenu = mutableStateOf(0)

    val selectedMenu
        get() = _selectedMenu.value

    fun changeSelectedMenu(int: Int) {
        _selectedMenu.value = int
    }

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _nutritionStatusState = mutableStateOf(ChildNutritionListState())
    val nutritionStatusState: State<ChildNutritionListState> = _nutritionStatusState

    private val _nutritionStandardState = mutableStateOf(ChildNutritionListState())
    val nutritionStandardState: State<ChildNutritionListState> = _nutritionStandardState

    private val _educationState = mutableStateOf(EducationState())
    val educationState: State<EducationState> = _educationState

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

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

    fun getEducationList(token: String) {
        getEducationListUseCase(token).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _educationState.value = EducationState(educationList = result.data?.map {
                        EducationDto(
                            author = it.author,
                            content = it.content,
                            desc = it.desc,
                            id = it.id,
                            publishedAt = DateUtils.formatDateTimeToIndonesianTimeDate(it.publishedAt),
                            title = it.title,
                            urlToImage = it.urlToImage
                        )
                    } ?: emptyList())
                }
                is Resource.Error -> {
                    _educationState.value = EducationState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    validationEventChannel.send(
                        ValidationEvent.Failed(result.message)
                    )
                }
                is Resource.Loading -> {
                    _educationState.value = EducationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUserDetail(token: String) {
        getUserDetailUseCase(token).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _profileState.value = ProfileState(userDetail = result.data)
                }
                is Resource.Error -> {
                    _profileState.value = ProfileState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _profileState.value = ProfileState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteUserToken() {
        viewModelScope.launch {
            deleteUserTokenUseCase.execute()
        }
    }

    sealed class ValidationEvent {
        data class Failed(val message: String?): ValidationEvent()
    }
}