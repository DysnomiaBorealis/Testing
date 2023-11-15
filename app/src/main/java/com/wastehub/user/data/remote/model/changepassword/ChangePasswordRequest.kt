package com.wastehub.user.data.remote.model.changepassword

data class ChangePasswordRequest(
    val oldPassword: String?,
    val newPassword: String?
)