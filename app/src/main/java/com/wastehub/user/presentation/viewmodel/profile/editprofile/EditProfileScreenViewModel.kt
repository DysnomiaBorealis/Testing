package com.wastehub.user.presentation.viewmodel.profile.editprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wastehub.user.data.remote.model.changeprofile.AddressUpdateRequest
import com.wastehub.user.data.remote.model.changeprofile.UserUpdateResult
import com.wastehub.user.domain.model.changeprofile.UserProfileData
import com.wastehub.user.domain.repository.UserRepository
import com.wastehub.user.domain.usecase.ValidationResult
import com.wastehub.user.domain.usecase.changeprofile.ChangeProfileUseCase
import com.wastehub.user.domain.usecase.changeprofile.ValidateProfileInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileScreenViewModel@Inject constructor(
    private val userRepository: UserRepository,
    private val changeProfileUseCase: ChangeProfileUseCase,
    private val validateProfileInput: ValidateProfileInputUseCase
) : ViewModel() {

    // Original profile data
    var originalUserProfile: UserProfileData? = null
    var originalAddress: String = ""
    var originalFullName: String = ""
    var originalEmail: String = ""
    var originalPhoneNumber: String = ""
    var originalAddressDetails: AddressUpdateRequest = AddressUpdateRequest()
    val updateStatus = MutableLiveData<UserUpdateResult>()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val userId = "user_id_here" // Replace with the actual user ID
            val userProfile = userRepository.getUserProfile(userId)
            if (userProfile != null) {
                originalUserProfile = userProfile
                originalFullName = userProfile.name
                originalEmail = userProfile.email
                originalPhoneNumber = userProfile.phoneNumber
                originalAddress = userProfile.address
                originalAddressDetails = userProfile.addressDetails
            } else {
                updateStatus.value = UserUpdateResult.Error("Failed to load user data.")
            }
        }
    }

    fun updateUserProfile(userId: String, fullName: String, email: String, phoneNumber: String, role: String, avatar: String, address: String, addressDetails: AddressUpdateRequest) {
        viewModelScope.launch {
            val fullNameValidation = validateProfileInput.validateFullName(fullName, originalUserProfile?.name ?: "")
            val emailValidation = validateProfileInput.validateEmail(email, originalUserProfile?.email ?: "")
            val phoneNumberValidation = validateProfileInput.validatePhoneNumber(phoneNumber, originalUserProfile?.phoneNumber ?: "")
            val addressValidation = validateProfileInput.validateAddress(address, originalAddress)
            val addressDetailsValidation = validateProfileInput.validateAddressDetails(addressDetails, originalAddressDetails)


            if (fullNameValidation.isSuccessful && emailValidation.isSuccessful && phoneNumberValidation.isSuccessful && addressValidation.isSuccessful && addressDetailsValidation.isSuccessful) {
                try {
                    val updatedUserProfile = changeProfileUseCase(userId, fullName, email, phoneNumber, role, avatar)
                    updateStatus.value = UserUpdateResult.Success(updatedUserProfile)
                } catch (e: Exception) {
                    updateStatus.value = UserUpdateResult.Error(e.message ?: "Unknown error")
                }
            } else {
                val errorMessage = listOf(
                    fullNameValidation.errorMessage,
                    emailValidation.errorMessage,
                    phoneNumberValidation.errorMessage,
                    addressValidation.errorMessage,
                    addressDetailsValidation.errorMessage
                ).filterNotNull().joinToString(", ")
                updateStatus.value = UserUpdateResult.Error("Validation failed: $errorMessage")
            }
        }
    }
    fun validateFullName(fullName: String): ValidationResult {
        return validateProfileInput.validateFullName(fullName, originalUserProfile?.name ?: "")
    }

    fun validateEmail(email: String): ValidationResult {
        return validateProfileInput.validateEmail(email, originalUserProfile?.email ?: "")
    }

    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        return validateProfileInput.validatePhoneNumber(phoneNumber, originalUserProfile?.phoneNumber ?: "")
    }

    fun validateAddress(address: String): ValidationResult {
        return validateProfileInput.validateAddress(address, originalAddress)
    }

    fun validateCoordinates(coordinates: String): ValidationResult {
        val coordinatePattern = """^(-?\d+(\.\d+)?),\s*(-?\d+(\.\d+)?)$""".toRegex()

        if (!coordinates.matches(coordinatePattern)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Coordinates are in an invalid format"
            )
        }

        val (latitude, longitude) = coordinates.split(",").map { it.trim().toDouble() }

        if (latitude !in -90.0..90.0 || longitude !in -180.0..180.0) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Coordinates are out of range"
            )
        }

        return ValidationResult(isSuccessful = true)
    }

    fun validateAddressDetails(addressDetails: AddressUpdateRequest): ValidationResult {
        return validateProfileInput.validateAddressDetails(addressDetails, originalAddressDetails)
    }



}