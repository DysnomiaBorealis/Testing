package com.wastehub.user.domain.usecase.login

import com.wastehub.user.domain.usecase.ValidationResult
import javax.inject.Inject

class ValidateLoginInputUseCase @Inject constructor() {
    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nomor telepon tidak boleh kosong"
            )
        }
        return ValidationResult(isSuccessful = true)
    }

    fun validatePassword(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password tidak boleh kosong"
            )
        }
        return ValidationResult(isSuccessful = true)
    }
}