package com.wastehub.user.data.remote.model.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String
)