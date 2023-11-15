package com.wastehub.user.domain.model.auth.register


data class UserData (
    val userId: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String
)