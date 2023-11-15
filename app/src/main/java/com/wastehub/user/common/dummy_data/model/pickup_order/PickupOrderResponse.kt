package com.wastehub.user.common.dummy_data.model.pickup_order

data class PickupOrderResponse(
    val data: PickupOrderData
)

data class PickupOrderData(
    val pickupOrders: List<PickupOrder>
)