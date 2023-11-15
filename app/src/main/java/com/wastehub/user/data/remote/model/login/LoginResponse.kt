package com.wastehub.user.data.remote.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data") val data: UserAccessNetwork,
    val message: String,
    val success: Boolean
)