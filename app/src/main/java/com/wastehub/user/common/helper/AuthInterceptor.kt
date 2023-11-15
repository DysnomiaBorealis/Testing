package com.wastehub.user.common.helper

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.wastehub.user.common.utils.SessionManager
import com.wastehub.user.data.remote.services.ApiServices
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor(
    private val sessionManager: SessionManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            sessionManager.getAccessToken().first()
        }
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}