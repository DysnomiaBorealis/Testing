package com.wastehub.user.data.remote.model.login

data class UserAccessNetwork(
    val accessToken: String?,
    val accountId: String?,
    val refreshToken: String?,
    val role: String?
)