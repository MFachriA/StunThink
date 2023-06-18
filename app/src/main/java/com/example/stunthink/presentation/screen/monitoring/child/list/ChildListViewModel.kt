package com.example.stunthink.presentation.screen.monitoring.child.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.data.remote.dto.child.ChildDto
import com.example.stunthink.domain.common.Resource
import com.example.stunthink.domain.use_case.monitoring.child.GetChildListUseCase
import com.example.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.example.stunthink.utils.DateUtils
import com.example.stunthink.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildListViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getChildListUseCase: GetChildListUseCase
): ViewModel() {
    private val _state = mutableStateOf(ChildListState())
    val state: State<ChildListState> = _state

    init {
        observeUserToken()
    }

    private fun observeUserToken() {
        viewModelScope.launch {
            getUserTokenUseCase.execute().collect { token ->
                // Call getCoins() again when userTokenFlow emits a new value
                if (token != null) {
                    getChilds(token)
                }
            }
        }
    }

    private fun getChilds(token: String) {
        getChildListUseCase(token).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ChildListState(childList = result.data?.map {
                        ChildDto(
                            id = it.id,
                            jenisKelamin = StringUtils.convertGenderEnum(it.jenisKelamin),
                            namaLengkap = it.namaLengkap,
                            parentId = it.parentId,
                            tanggalLahir = DateUtils.formatDateToIndonesianDate(it.tanggalLahir),
                            tempatLahir = it.tempatLahir
                        )
                    } ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ChildListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ChildListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}