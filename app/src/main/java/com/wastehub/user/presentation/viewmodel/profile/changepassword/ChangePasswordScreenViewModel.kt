package com.wastehub.user.presentation.viewmodel.profile.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wastehub.user.domain.repository.UserRepository
import com.wastehub.user.domain.usecase.ValidationResult
import com.wastehub.user.domain.usecase.changepassword.ValidateChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val validateChangePasswordUseCase: ValidateChangePasswordUseCase
) : ViewModel() {

    private val _validationResult = MutableLiveData<ValidationResult>()
    val validationResult: LiveData<ValidationResult> get() = _validationResult

    private val _updateStatus = MutableLiveData<String>()
    val updateStatus: LiveData<String> = _updateStatus

    fun validateAndChangePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val confirmValidation = validateChangePasswordUseCase.validateConfirmPassword(confirmPassword)
        val newPasswordValidation = validateChangePasswordUseCase.validatePassword(newPassword, oldPassword)
        val matchValidation = validateChangePasswordUseCase.validatePasswordAndConfirmPasswordMatches(newPassword, confirmPassword)

        if (confirmValidation.isSuccessful && newPasswordValidation.isSuccessful && matchValidation.isSuccessful) {
            viewModelScope.launch {
                try {
                    val changeResponse = userRepository.changePassword(oldPassword, newPassword)
                    if (changeResponse.success) {
                        _updateStatus.value = "Password changed successfully"
                    } else {
                        _updateStatus.value = changeResponse.message ?: "Error changing password"
                    }
                } catch (e: Exception) {
                    _updateStatus.value = e.message ?: "Unknown error occurred"
                }
            }
        } else {
            val errorMessage = listOf(
                confirmValidation.errorMessage,
                newPasswordValidation.errorMessage,
                matchValidation.errorMessage
            ).filterNotNull().joinToString("\n")
            _validationResult.value = ValidationResult(false, errorMessage)
        }
    }
}