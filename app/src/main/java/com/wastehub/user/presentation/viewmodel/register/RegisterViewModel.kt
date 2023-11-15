package com.wastehub.user.presentation.viewmodel.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wastehub.user.data.remote.model.login.LoginResult
import com.wastehub.user.data.remote.model.register.RegisterResult
import com.wastehub.user.domain.usecase.ValidationResult
import com.wastehub.user.domain.usecase.register.RegisterUseCase
import com.wastehub.user.domain.usecase.register.ValidateRegisterInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val register: ValidateRegisterInputUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> get() = _registerResult

    fun registerUser(fullName: String, password: String, email: String, phoneNumber: String) {
        viewModelScope.launch {
            try {
                val result = registerUseCase(fullName, password, email, phoneNumber)
                _registerResult.value = RegisterResult.Success(result)
            } catch (e: Exception) {
                _registerResult.value = RegisterResult.Error("Register failed: ${e.message}")
            }
        }
    }

    fun validateFullName(fullName: String): ValidationResult {
        return register.validateFullName(fullName)
    }

    fun validateEmail(email: String): ValidationResult {
        return register.validateEmail(email)
    }

    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        return register.validatePhoneNumber(phoneNumber)
    }

    fun validatePassword(password: String): ValidationResult {
        return register.validatePassword(password)
    }

    fun validateConfirmPassword(confirmPassword: String): ValidationResult {
        return register.validateConfirmPassword(confirmPassword)
    }

    fun validatePasswordAndConfirmPasswordMatches(password: String, confirmPassword: String): ValidationResult {
        return register.validatePasswordAndConfirmPasswordMatches(password, confirmPassword)
    }
}