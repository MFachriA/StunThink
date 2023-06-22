package com.projectAnya.stunthink.domain.use_case.validate

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
