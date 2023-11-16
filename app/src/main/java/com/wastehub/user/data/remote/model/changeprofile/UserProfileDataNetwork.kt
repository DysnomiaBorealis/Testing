package com.wastehub.user.data.remote.model.changeprofile

data class UserProfileDataNetwork(
    val _id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val role: String,
    val avatar: String,
    val address: String,
    val addressDetails: AddressUpdateRequest
)