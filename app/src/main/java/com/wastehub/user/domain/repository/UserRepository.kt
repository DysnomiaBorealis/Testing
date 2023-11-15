package com.wastehub.user.domain.repository

import com.wastehub.user.data.remote.model.changepassword.ChangePasswordResponse
import com.wastehub.user.domain.model.auth.forgotpassword.ForgotPassword
import com.wastehub.user.domain.model.auth.login.UserAccess
import com.wastehub.user.domain.model.auth.register.UserData

interface UserRepository {
    suspend fun loginUser(phoneNumber: String, password: String): UserAccess

    suspend fun registerUser(fullName: String, password: String, email: String, phoneNumber: String): UserData

    suspend fun forgotPasswordUser(phoneNumber: String): ForgotPassword

    suspend fun changePassword(oldPassword: String, newPassword: String): ChangePasswordResponse}