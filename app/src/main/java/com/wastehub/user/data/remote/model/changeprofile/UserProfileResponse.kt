package com.wastehub.user.data.remote.model.changeprofile

data class UserProfileResponse(
    val success: Boolean,
    val message: String,
    val data: UserProfileDataNetwork
)
