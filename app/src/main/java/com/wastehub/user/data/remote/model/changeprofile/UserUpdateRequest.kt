package com.wastehub.user.data.remote.model.changeprofile
data class UserUpdateRequest(
        val name: String? = null,
        val email: String? = null,
        val phoneNumber: String? = null,
        val address: AddressUpdateRequest? = null
    )
