package com.wastehub.user.domain.usecase.register

import android.util.Patterns
import com.wastehub.user.domain.usecase.ValidationResult
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateRegisterInputUseCase @Inject constructor() {
    fun validateFullName(fullName: String): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nama tidak boleh kosong"
            )
        }

        return ValidationResult(isSuccessful = true)
    }

    fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Email tidak boleh kosong"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Email tidak valid"
            )
        }

        return ValidationResult(isSuccessful = true)
    }

    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nomor telepon tidak boleh kosong"
            )
        }

        if (phoneNumber.length < 10 || phoneNumber.length > 13) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nomor telepon tidak valid"
            )
        }

        if (!phoneNumber.startsWith("+628")) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nomor telepon tidak valid"
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
