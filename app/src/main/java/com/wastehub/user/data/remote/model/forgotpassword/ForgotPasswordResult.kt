package com.wastehub.user.data.remote.model.forgotpassword

import com.wastehub.user.domain.model.auth.forgotpassword.ForgotPassword

sealed class ForgotPasswordResult {
    data class Success(val forgotPassword: ForgotPassword): ForgotPasswordResult()
    data class Error(val error: String): ForgotPasswordResult()
}