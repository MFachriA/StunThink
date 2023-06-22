package com.projectAnya.stunthink.presentation.screen.monitoring.child.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.use_case.monitoring.child.GetChildListUseCase
import com.projectAnya.stunthink.utils.DateUtils
import com.projectAnya.stunthink.utils.StringUtils
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
                is com.projectAnya.stunthink.domain.common.Resource.Success -> {
                    _state.value = ChildListState(childList = result.data?.map {
                        com.projectAnya.stunthink.data.remote.dto.child.ChildDto(
                            id = it.id,
                            jenisKelamin = StringUtils.convertGenderEnum(it.jenisKelamin),
                            namaLengkap = it.namaLengkap,
                            parentId = it.parentId,
                            tanggalLahir = DateUtils.formatDateToIndonesianDate(it.tanggalLahir),
                            tempatLahir = it.tempatLahir
                        )
                    } ?: emptyList())
                }
                is com.projectAnya.stunthink.domain.common.Resource.Error -> {
                    _state.value = ChildListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is com.projectAnya.stunthink.domain.common.Resource.Loading -> {
                    _state.value = ChildListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}