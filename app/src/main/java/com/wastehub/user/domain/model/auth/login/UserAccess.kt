package com.wastehub.user.domain.model.auth.login

data class UserAccess(
    val accessToken: String,
    val refreshToken: String,
    val accountId: String,
    val role: String
)
