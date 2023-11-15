package com.wastehub.user.data.remote.model.token


data class UpdateTokenData(
    val accessToken: String?,
    val refreshToken: String?
)