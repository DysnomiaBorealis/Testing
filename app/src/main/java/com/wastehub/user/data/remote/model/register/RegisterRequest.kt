package com.wastehub.user.data.remote.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val fullName: String?,
    val phoneNumber: String?,
    val email: String?,
    val password: String?
)