package com.projectAnya.stunthink.presentation.screen.main.profile

import com.projectAnya.stunthink.data.remote.dto.user.UserDto

data class ProfileState(
    val isLoading: Boolean = false,
    val userDetail: UserDto? = null,
    val error: String = ""
)
