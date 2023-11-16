package com.wastehub.user.common.helper

import com.wastehub.user.data.remote.model.changeprofile.UserProfileDataNetwork
import com.wastehub.user.data.remote.model.forgotpassword.ForgotPasswordData
import com.wastehub.user.data.remote.model.login.UserAccessNetwork
import com.wastehub.user.data.remote.model.register.UserDataNetwork
import com.wastehub.user.domain.model.auth.forgotpassword.ForgotPassword
import com.wastehub.user.domain.model.auth.login.UserAccess
import com.wastehub.user.domain.model.auth.register.UserData
import com.wastehub.user.domain.model.changeprofile.UserProfileData

object Mapper {
    fun loginResponseToDomain(response: UserAccessNetwork): UserAccess {
        return UserAccess(
            accessToken = response.accessToken ?: "",
            refreshToken = response.refreshToken ?: "",
            accountId = response.accountId ?: "",
            role = response.role ?: ""
        )
    }

    fun registerResponseToDomain(response: UserDataNetwork): UserData {
        return UserData(
            userId = response.userId ?: "",
            fullName = response.fullName ?: "",
            email = response.email ?: "",
            phoneNumber = response.phoneNumber ?: ""
        )
    }

    fun forgotPasswordResponseToDomain(response: ForgotPasswordData): ForgotPassword {
        return ForgotPassword(
            email = response.email ?: "",
        )
    }

    fun userProfileResponseToDomain(response: UserProfileDataNetwork): UserProfileData {
        return UserProfileData(
            _id = response._id,
            name = response.name,
            email = response.email,
            phoneNumber = response.phoneNumber,
            role = response.role,
            avatar = response.avatar,
            address = response.address,
            addressDetails = response.addressDetails
        )
    }

}