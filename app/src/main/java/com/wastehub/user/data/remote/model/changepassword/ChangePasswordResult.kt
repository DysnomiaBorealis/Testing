package com.wastehub.user.data.remote.model.changepassword

sealed class ChangePasswordResult {
    data class Success(val response: ChangePasswordResponse): ChangePasswordResult()
    data class Error(val error: String): ChangePasswordResult()
}