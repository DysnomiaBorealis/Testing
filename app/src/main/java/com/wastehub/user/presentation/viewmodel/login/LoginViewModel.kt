package com.wastehub.user.presentation.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wastehub.user.data.remote.model.forgotpassword.ForgotPasswordResult
import com.wastehub.user.data.remote.model.login.LoginResult
import com.wastehub.user.domain.model.auth.forgotpassword.ForgotPassword
import com.wastehub.user.domain.usecase.ValidationResult
import com.wastehub.user.domain.usecase.forgotpassword.ForgotPasswordUseCase
import com.wastehub.user.domain.usecase.login.LoginUseCase
import com.wastehub.user.domain.usecase.login.ValidateLoginInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: ValidateLoginInputUseCase,
    private val loginUseCase: LoginUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
): ViewModel() {


    private val _forgotPasswordResult = MutableLiveData<ForgotPasswordResult>()
    val forgotPasswordResult: LiveData<ForgotPasswordResult> get() = _forgotPasswordResult
    fun forgotPasswordUser(phoneNumber: String) {
        viewModelScope.launch {
            try {
                val result = forgotPasswordUseCase(phoneNumber)
                _forgotPasswordResult.value = ForgotPasswordResult.Success(result)
            } catch (e: Exception) {
                _forgotPasswordResult.value = ForgotPasswordResult.Error("Reset Password failed: ${e.message}")
            }
        }
    }

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> get() = _loginResult
    fun loginUser(phoneNumber: String, password: String) {
        viewModelScope.launch {
            try {
                val result = loginUseCase(phoneNumber, password)
                _loginResult.value = LoginResult.Success(result)
            } catch (e: Exception) {
                _loginResult.value = LoginResult.Error("Login failed: ${e.message}")
            }
        }
    }

    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        return login.validatePhoneNumber(phoneNumber)
    }

    fun validatePassword(password: String): ValidationResult {
        return login.validatePassword(password)
    }

}