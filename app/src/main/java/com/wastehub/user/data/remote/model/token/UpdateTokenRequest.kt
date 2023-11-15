package com.wastehub.user.data.remote.model.token

import com.google.gson.annotations.SerializedName

data class UpdateTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String?
)