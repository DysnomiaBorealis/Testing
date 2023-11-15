package com.wastehub.user.data.remote.model.forgotpassword

data class ForgotPasswordResponse(
    val data: ForgotPasswordData,
    val message: String,
    val success: Boolean
)