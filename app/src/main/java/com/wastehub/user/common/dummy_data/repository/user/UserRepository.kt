package com.wastehub.user.common.dummy_data.repository.user

import android.content.Context
import com.google.gson.Gson
import com.wastehub.user.R
import com.wastehub.user.common.dummy_data.model.user.User
import com.wastehub.user.common.dummy_data.model.user.UserResponse

class UserRepository (private val context: Context) {
    fun getMockUser(): List<User>? {
        val json = getJsonFromRawResource(context, R.raw.user)
        if (json != null) {
            val gson = Gson()
            val response = gson.fromJson(json, UserResponse::class.java)
            return response.data.users
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