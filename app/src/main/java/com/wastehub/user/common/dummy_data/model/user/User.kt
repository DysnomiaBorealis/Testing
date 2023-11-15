package com.wastehub.user.common.dummy_data.model.user

data class User(
    val _id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val role: String,
    val avatar: String,
    val address: Address,
    val location: Location,
    val createdAt: String,
    val updatedAt: String,
    val membership: Membership
)

data class Address(
    val note: String,
    val subvillage: String,
    val village: String,
    val district: String,
    val regency: String,
    val province: String
)

data class Location(
    val latitude: Double,
    val longitude: Double
)

data class Membership(
    val id: String,
    val status: String,
    val startDate: String,
    val endDate: String,
    val wasteBank: WasteBank
)

data class WasteBank(
    val name: String
)