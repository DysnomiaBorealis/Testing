package com.wastehub.user.domain.usecase.changeprofile

import android.util.Patterns
import com.wastehub.user.data.remote.model.changeprofile.AddressUpdateRequest
import com.wastehub.user.domain.usecase.ValidationResult
import javax.inject.Inject

class ValidateProfileInputUseCase @Inject constructor() {

    fun validateFullName(fullName: String, originalFullName: String): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nama tidak boleh kosong"
            )
        }
        if (!hasChanged(fullName, originalFullName)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nama sama dengan sebelumnya"
            )
        }
        return ValidationResult(isSuccessful = true)
    }

    fun validateEmail(email: String, originalEmail: String): ValidationResult {
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
        if (!hasChanged(email, originalEmail)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Email sama dengan sebelumnya"
            )
        }
        return ValidationResult(isSuccessful = true)
    }

    fun validatePhoneNumber(phoneNumber: String, originalPhoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nomor telepon tidak boleh kosong"
            )
        }
        if (!hasChanged(phoneNumber, originalPhoneNumber)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Nomor telepon sama dengan sebelumnya"
            )
        }
        return ValidationResult(isSuccessful = true)
    }

    fun validateAddress(address: String, originalAddress: String): ValidationResult {
        if (address.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Alamat tidak boleh kosong"
            )
        }
        if (!hasChanged(address, originalAddress)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Alamat sama dengan sebelumnya"
            )
        }
        return ValidationResult(isSuccessful = true)
    }

    fun validateAddressDetails(addressDetails: AddressUpdateRequest, originalAddressDetails: AddressUpdateRequest): ValidationResult {
        if (addressDetails.note.isNullOrBlank()) {
            return ValidationResult(false, "Alamat tidak boleh kosong")
        }

        if (addressDetails == originalAddressDetails) {
            return ValidationResult(false, "Alamat sama dengan sebelumnya")
        }

        return ValidationResult(true)
    }

    fun validateCoordinates(coordinates: String): ValidationResult {
        val coordinatePattern = """^(-?\d+(\.\d+)?),\s*(-?\d+(\.\d+)?)$""".toRegex()

        if (!coordinates.matches(coordinatePattern)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Koordinat tidak valid"
            )
        }

        return ValidationResult(isSuccessful = true)
    }

    private fun hasChanged(newValue: String, originalValue: String): Boolean {
        return newValue != originalValue
    }
}