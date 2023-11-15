package com.wastehub.user.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wastehub.user.data.remote.model.login.LoginResult
import com.wastehub.user.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _changePasswordResult = MutableLiveData<String>()
    val changePasswordResult: LiveData<String> get() = _changePasswordResult

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val result = repository.changePassword(oldPassword, newPassword)
                Log.d("CHANGE PASSWORD", "${result.success}  ${result.message}")
                _changePasswordResult.value = result.message
            } catch (e: Exception) {
                _changePasswordResult.value = e.message
            }
        }
    }
}