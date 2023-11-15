package com.wastehub.user.common.dummy_data.model.user

data class UserResponse(
    val data: UserData
)

data class UserData(
    val users: List<User>
)