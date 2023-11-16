package com.wastehub.user.domain.model.changeprofile

import com.wastehub.user.data.remote.model.changeprofile.AddressUpdateRequest

data class UserProfileData(
    val _id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val role: String,
    val avatar: String,
    val address: String,
    val addressDetails: AddressUpdateRequest
)
