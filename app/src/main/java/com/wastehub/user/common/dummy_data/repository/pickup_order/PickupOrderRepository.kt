package com.wastehub.user.common.dummy_data.repository.pickup_order

import android.content.Context
import com.google.gson.Gson
import com.wastehub.user.R
import com.wastehub.user.common.dummy_data.model.pickup_order.PickupOrder
import com.wastehub.user.common.dummy_data.model.pickup_order.PickupOrderResponse

class PickupOrderRepository(private val context: Context) {
    fun getMockPickupOrders(): List<PickupOrder>? {
        val json = getJsonFromRawResource(context, R.raw.pickup_order)
        if (json != null) {
            val gson = Gson()
            val response = gson.fromJson(json, PickupOrderResponse::class.java)
            return response.data.pickupOrders
        }
        return null
    }

    private fun getJsonFromRawResource(context: Context, resourceId: Int): String? {
        return try {
            val inputStream = context.resources.openRawResource(resourceId)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}