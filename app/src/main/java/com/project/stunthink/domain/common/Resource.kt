package com.project.stunthink.domain.common

sealed class Resource<T>(val success: Boolean? = null, val message: String? = null, val data: T? = null) {
    class Success<T>(success: Boolean, message: String, data: T) : Resource<T>(
        success, message, data
    )
    class Error<T>(success: Boolean? = null, message: String, data: T? = null) : Resource<T>(
        success, message, data
    )
    class Loading<T>(data: T? = null) : Resource<T>(data = data)
}