package com.wastehub.user.common.dummy_data.model.pickup_order

data class PickupOrder(
    val _id: String,
    val user: UserData,
    val picker: UserData,
    val pickupDate: String,
    val status: String,
    val wasteType: WasteType,
    val wasteWeight: Double,
    val wasteVolume: WasteVolume,
    val notes: String,
    val createdAt: String,
    val updatedAt: String
)

data class UserData(
    val _id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val role: String,
    val avatar: String,
    val address: Address,
    val location: Location,
    val createdAt: String,
    val updatedAt: String
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

data class WasteType(
    val _id: String,
    val name: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String
)

data class WasteVolume(
    val _id: String,
    val name: String,
    val description: String,
    val minWeight: Double,
    val maxWeight: Double,
    val pickupFee: Double,
    val createdAt: String,
    val updatedAt: String
)
