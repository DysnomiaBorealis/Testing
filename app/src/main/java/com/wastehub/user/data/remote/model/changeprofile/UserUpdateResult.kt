package com.wastehub.user.data.remote.model.changeprofile

import com.wastehub.user.data.remote.model.forgotpassword.ForgotPasswordResult
import com.wastehub.user.domain.model.changeprofile.UserProfileData

sealed class UserUpdateResult {
    data class Success(val userProfileData: UserProfileData): UserUpdateResult ()

    data class Error(val error: String): UserUpdateResult()

}