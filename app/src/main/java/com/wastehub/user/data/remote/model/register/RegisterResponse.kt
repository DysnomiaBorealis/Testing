package com.wastehub.user.data.remote.model.register

data class RegisterResponse(
    val data: UserDataNetwork,
    val message: String,
    val success: Boolean
)