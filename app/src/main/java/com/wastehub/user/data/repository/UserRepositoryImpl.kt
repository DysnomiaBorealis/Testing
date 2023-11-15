package com.wastehub.user.data.repository

import com.wastehub.user.common.helper.Mapper
import com.wastehub.user.data.remote.model.changepassword.ChangePasswordRequest
import com.wastehub.user.data.remote.model.changepassword.ChangePasswordResponse
import com.wastehub.user.data.remote.model.forgotpassword.ForgotPasswordRequest
import com.wastehub.user.data.remote.model.login.LoginRequest
import com.wastehub.user.data.remote.model.register.RegisterRequest
import com.wastehub.user.data.remote.services.ApiServices
import com.wastehub.user.domain.model.auth.forgotpassword.ForgotPassword
import com.wastehub.user.domain.model.auth.login.UserAccess
import com.wastehub.user.domain.model.auth.register.UserData
import com.wastehub.user.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
): UserRepository {
    override suspend fun loginUser(phoneNumber: String, password: String): UserAccess {
        val loginResponse = apiServices.loginUser(LoginRequest(phoneNumber, password))
        return Mapper.loginResponseToDomain(loginResponse.data)
    }

    override suspend fun registerUser(
        fullName: String,
        password: String,
        email: String,
        phoneNumber: String
    ): UserData {
        val registerResponse = apiServices.registerUser(RegisterRequest(fullName, phoneNumber, email, password))
        return Mapper.registerResponseToDomain(registerResponse.data)
    }

    override suspend fun forgotPasswordUser(phoneNumber: String): ForgotPassword {
        val forgotPasswordResponse = apiServices.forgotPassword(ForgotPasswordRequest(phoneNumber))
        return Mapper.forgotPasswordResponseToDomain(forgotPasswordResponse.data)
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): ChangePasswordResponse {
        return apiServices.changePassword(ChangePasswordRequest(oldPassword, newPassword))
    }

}