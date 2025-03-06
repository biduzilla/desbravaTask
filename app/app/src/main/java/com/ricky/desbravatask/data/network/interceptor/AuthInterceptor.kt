package com.ricky.desbravatask.data.network.interceptor

import com.ricky.desbravatask.data.local.DataStoreUtil
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseRefreshToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreUtil: DataStoreUtil,
    private val refreshTokenCase: UseCaseRefreshToken
) : Interceptor {

    private var token = ""

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreUtil.getToken().collect {
                it?.let {
                    token = "Bearer ${it.token}"
                }
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.isSuccessful || response.code != 403) {
            return response
        }

        response.close()

        if (token.isNotBlank()) {
            val newRequest = request
                .newBuilder()
                .addHeader("Authorization", token)
                .build()
            val responseToken = chain.proceed(newRequest)
            return if (responseToken.code == 403) {
                responseToken.close()
                return refreshToken(chain)
            } else {
                responseToken
            }
        } else {
            return refreshToken(chain)
        }
    }

    private fun refreshToken(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null

        runBlocking(Dispatchers.IO) {
            refreshTokenCase()?.let {
                val token = "Bearer ${it.token}"
                dataStoreUtil.saveToken(it)

                val newRequest = request
                    .newBuilder()
                    .header("Authorization", token)
                    .build()
                response = chain.proceed(newRequest)
            }
        }
        return response ?: chain.proceed(request)
    }
}