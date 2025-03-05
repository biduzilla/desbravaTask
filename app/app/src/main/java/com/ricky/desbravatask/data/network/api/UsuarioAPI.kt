package com.ricky.desbravatask.data.network.api

import com.ricky.desbravatask.domain.models.Login
import com.ricky.desbravatask.domain.models.PageModel
import com.ricky.desbravatask.domain.models.ResetSenha
import com.ricky.desbravatask.domain.models.Token
import com.ricky.desbravatask.domain.models.Usuario
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import com.ricky.desbravatask.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuarioAPI {
    @POST(Constants.USER_ENDPOINT)
    suspend fun save(@Body usuario: Usuario): Response<UsuarioUpdate>

    @GET(Constants.USER_ENDPOINT)
    suspend fun getAll(
        @Query("search") search: String?,
        @Query("size") size: Int = 0,
        @Query("page") page: Int = 0
    ): Response<PageModel<UsuarioUpdate>>

    @GET("${Constants.USER_ENDPOINT}/{id}")
    suspend fun getById(@Path("id") id: String): Response<UsuarioUpdate>

    @POST(Constants.USER_LOGIN_ENDPOINT)
    suspend fun login(@Body login: Login): Response<Token>

    @POST(Constants.USER_REFRESH_TOKEN_ENDPOINT)
    suspend fun refreshToken(@Body token: Token): Response<Token>

    @PUT(Constants.USER_ENDPOINT)
    suspend fun update(@Body usuario: UsuarioUpdate): Response<UsuarioUpdate>

    @DELETE("${Constants.USER_ENDPOINT}/{id}")
    suspend fun delete(@Path("id") id: String): Response<Void>

    @GET("${Constants.USER_VERIFICAR_COD_ENDPOINT}/{cod}/{email}")
    suspend fun verificarCod(@Path("cod") cod: Int, @Path("email") email: String): Response<Void>

    @PUT("${Constants.USER_RESET_SENHA_ENDPOINT}/{email}")
    suspend fun resetSenha(@Path("email") email: String): Response<Void>

    @PUT(Constants.USER_ALTERAR_SENHA_ENDPOINT)
    suspend fun alterarSenha(@Body resetSenha: ResetSenha): Response<Void>
}