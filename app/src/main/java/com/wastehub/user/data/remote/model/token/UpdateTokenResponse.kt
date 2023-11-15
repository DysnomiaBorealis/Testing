package com.wastehub.user.data.remote.model.token

import com.google.gson.annotations.SerializedName

data class UpdateTokenResponse(
    @SerializedName("data")
    val data: UpdateTokenData,
    val message: String,
    val success: Boolean
)