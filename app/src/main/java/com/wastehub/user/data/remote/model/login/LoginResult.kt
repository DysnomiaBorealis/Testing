package com.wastehub.user.data.remote.model.login

import com.wastehub.user.domain.model.auth.login.UserAccess

sealed class LoginResult {
    data class Success(val userAccess: UserAccess): LoginResult()
    data class Error(val error: String): LoginResult()
}