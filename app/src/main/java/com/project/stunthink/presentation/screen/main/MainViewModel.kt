package com.project.stunthink.presentation.screen.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.stunthink.data.remote.dto.education.EducationDto
import com.project.stunthink.domain.common.Resource
import com.project.stunthink.domain.use_case.education.GetEducationListUseCase
import com.project.stunthink.presentation.screen.main.education.EducationState
import com.project.stunthink.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getEducationListUseCase: GetEducationListUseCase
): ViewModel() {

    private val _selectedMenu = mutableStateOf(0)

    val selectedMenu
        get() = _selectedMenu.value

    fun changeSelectedMenu(int: Int) {
        _selectedMenu.value = int
    }
    private val _state = mutableStateOf(EducationState())

    val state: State<EducationState> = _state

    fun getEducationList(token: String) {
        getEducationListUseCase(token).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = EducationState(educationList = result.data?.map {
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
                    _state.value = EducationState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = EducationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}