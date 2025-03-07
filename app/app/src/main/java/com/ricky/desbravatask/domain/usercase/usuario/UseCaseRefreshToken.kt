package com.ricky.desbravatask.domain.usercase.usuario

import android.util.Log
import com.ricky.desbravatask.data.local.DataStoreUtil
import com.ricky.desbravatask.domain.models.Token
import com.ricky.desbravatask.domain.repository.TokenRepository
import javax.inject.Inject

class UseCaseRefreshToken @Inject constructor(private val repository: TokenRepository,
                                              private val dataStoreUtil: DataStoreUtil
) {
    suspend operator fun invoke(): Token? {
        var refreshedToken: Token? = null

        dataStoreUtil.getToken().collect { storedToken ->
            storedToken?.let {
                try {
                    val result = repository.refreshToken(it)

                    if (result.isSuccessful) {
                        refreshedToken = result.body()
                    }
                } catch (e: Exception) {
                    Log.e("infoteste", e.localizedMessage ?: "Erro inesperado")
                }
            }
        }

        return refreshedToken
    }
}