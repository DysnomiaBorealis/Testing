package com.wastehub.user.data.remote.services

import com.wastehub.user.data.remote.model.changepassword.ChangePasswordRequest
import com.wastehub.user.data.remote.model.changepassword.ChangePasswordResponse
import com.wastehub.user.data.remote.model.forgotpassword.ForgotPasswordRequest
import com.wastehub.user.data.remote.model.forgotpassword.ForgotPasswordResponse
import com.wastehub.user.data.remote.model.login.LoginRequest
import com.wastehub.user.data.remote.model.login.LoginResponse
import com.wastehub.user.data.remote.model.register.RegisterRequest
import com.wastehub.user.data.remote.model.register.RegisterResponse
import com.wastehub.user.data.remote.model.token.UpdateTokenRequest
import com.wastehub.user.data.remote.model.token.UpdateTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiServices {

    @Headers("Content-Type:application/json")
    @POST("users/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): LoginResponse

    @Headers("Content-Type:application/json")
    @POST("users")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): RegisterResponse

    @Headers("Content-Type:application/json")
    @POST("users/forgot-password")
    suspend fun forgotPassword(
        @Body phoneNumber: ForgotPasswordRequest
    ): ForgotPasswordResponse

    @Headers("Content-Type:application/json")
    @POST("accounts/refresh-token")
    suspend fun updateToken(
        @Body refreshToken: UpdateTokenRequest
    ): UpdateTokenResponse

    @Headers("Content-Type:application/json")
    @PATCH("users/change-password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest
    ): ChangePasswordResponse

}