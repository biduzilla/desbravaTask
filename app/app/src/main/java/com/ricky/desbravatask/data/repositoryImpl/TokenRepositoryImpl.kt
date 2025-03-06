package com.ricky.desbravatask.data.repositoryImpl

import com.ricky.desbravatask.data.network.api.RefreshTokenAPI
import com.ricky.desbravatask.domain.models.Token
import com.ricky.desbravatask.domain.repository.TokenRepository
import retrofit2.Response
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(private val refreshTokenAPI: RefreshTokenAPI) :
    TokenRepository {
    override suspend fun refreshToken(token: Token): Response<Token> =
        refreshTokenAPI.refreshToken(token)

}