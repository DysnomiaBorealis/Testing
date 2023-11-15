package com.wastehub.user.common.helper

import com.wastehub.user.common.Constant
import com.wastehub.user.common.utils.SessionManager
import com.wastehub.user.data.remote.model.token.UpdateTokenRequest
import com.wastehub.user.data.remote.model.token.UpdateTokenResponse
import com.wastehub.user.data.remote.services.ApiServices
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val sessionManager: SessionManager
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking {
            sessionManager.getRefreshToken().first()
        }
        return runBlocking {
            val newToken = getNewToken(refreshToken)

            if (!newToken.success) {
                sessionManager.deleteToken()
            }

            newToken.data.let {
                sessionManager.saveToken(it.accessToken, it.refreshToken)
                response.request.newBuilder()
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): UpdateTokenResponse {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(ApiServices::class.java)
        return service.updateToken(UpdateTokenRequest(refreshToken))
    }
}