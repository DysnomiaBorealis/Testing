package com.wastehub.user.data.remote.model.register

import com.wastehub.user.domain.model.auth.register.UserData

sealed class RegisterResult {
    data class Success(val userData: UserData): RegisterResult()
    data class Error(val error: String): RegisterResult()
}