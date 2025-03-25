package com.ricky.desbravatask.data.network.api

import com.ricky.desbravatask.domain.models.Comentario
import com.ricky.desbravatask.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ComentarioAPI {
    @POST(Constants.COMENTARIO_ENDPOINT)
    suspend fun save(@Body comentario: Comentario): Response<Comentario>

    @GET("${Constants.COMENTARIO_BY_ID_TAREFA}/{idTarefa}")
    suspend fun getByIdTarefa(@Path("idTarefa") idTarefa: String): Response<List<Comentario>>

    @GET("${Constants.COMENTARIO_ENDPOINT}/{id}")
    suspend fun getById(@Path("id") id: String): Response<Comentario>

    @DELETE("${Constants.COMENTARIO_ENDPOINT}/{id}")
    suspend fun deleteById(@Path("id") id: String): Response<Void>
}