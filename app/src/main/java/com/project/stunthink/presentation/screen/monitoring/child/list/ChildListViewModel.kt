package com.project.stunthink.presentation.screen.monitoring.child.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.stunthink.domain.use_case.monitoring.child.GetChildListUseCase
import com.project.stunthink.utils.DateUtils
import com.project.stunthink.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChildListViewModel @Inject constructor(
    private val getChildListUseCase: GetChildListUseCase
): ViewModel() {
    private val _state = mutableStateOf(ChildListState())
    val state: State<ChildListState> = _state

    fun getChilds(token: String) {
        getChildListUseCase(token).onEach { result ->
            when (result) {
                is com.project.stunthink.domain.common.Resource.Success -> {
                    _state.value = ChildListState(childList = result.data?.map {
                        com.project.stunthink.data.remote.dto.child.ChildDto(
                            id = it.id,
                            jenisKelamin = StringUtils.convertGenderEnum(it.jenisKelamin),
                            namaLengkap = it.namaLengkap,
                            parentId = it.parentId,
                            tanggalLahir = DateUtils.formatDateToIndonesianDate(it.tanggalLahir),
                            tempatLahir = it.tempatLahir
                        )
                    } ?: emptyList())
                }
                is com.project.stunthink.domain.common.Resource.Error -> {
                    _state.value = ChildListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is com.project.stunthink.domain.common.Resource.Loading -> {
                    _state.value = ChildListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}