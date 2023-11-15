package com.wastehub.user.data.remote.model.register

import com.google.gson.annotations.SerializedName

data class UserDataNetwork(
    val __v: Int?,
    @SerializedName("_id")
    val userId: String?,
    val avatar: String?,
    val createdAt: String?,
    val email: String?,
    val membershipNetwork: MembershipNetwork?,
    @SerializedName("name")
    val fullName: String?,
    val phoneNumber: String?,
    val role: String?,
    val updatedAt: String?
)