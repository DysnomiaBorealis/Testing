package com.wastehub.user.domain.usecase

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)
