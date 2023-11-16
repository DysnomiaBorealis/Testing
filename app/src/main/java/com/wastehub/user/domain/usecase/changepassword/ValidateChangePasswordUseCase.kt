package com.wastehub.user.domain.usecase.changepassword

import com.wastehub.user.domain.usecase.ValidationResult
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateChangePasswordUseCase @Inject constructor() {

    fun validatePassword(password: String, oldPassword: String? = null): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password tidak boleh kosong"
            )
        }

        if (password.length <= 6) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password harus memiliki lebih dari 6 karakter"
            )
        }

        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password harus memiliki huruf besar"
            )
        }


        if (!Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]").matcher(password).find()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password harus memiliki simbol karakter khusus"
            )
        }

        if (password == oldPassword) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password baru tidak boleh sama dengan password sebelumnya"
            )
        }

        return ValidationResult(isSuccessful = true)
    }


    fun validateConfirmPassword(confirmPassword: String): ValidationResult {
        if (confirmPassword.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Konfirmasi password tidak boleh kosong"
            )
        }

        return ValidationResult(isSuccessful = true)
    }

    fun validatePasswordAndConfirmPasswordMatches(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Konfirmasi password tidak cocok dengan password anda"
            )
        }

        return ValidationResult(isSuccessful = true)
    }
}