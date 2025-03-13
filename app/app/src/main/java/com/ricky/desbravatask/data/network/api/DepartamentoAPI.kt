package com.ricky.desbravatask.data.network.api

import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DepartamentoAPI {
    @POST(Constants.DEPARTAMENTO_ENDPOINT)
    suspend fun save(@Body departamento: Departamento): Response<Departamento>

    @GET(Constants.DEPARTAMENTO_ENDPOINT)
    suspend fun getAll(@Query("userId") userId: String): Response<List<Departamento>>

    @GET("${Constants.DEPARTAMENTO_ENDPOINT}/{id}")
    suspend fun getById(@Path("id") id: String): Response<Departamento>

    @PUT(Constants.DEPARTAMENTO_ENDPOINT)
    suspend fun update(@Body departamento: Departamento): Response<Departamento>

    @DELETE("${Constants.DEPARTAMENTO_ENDPOINT}/{id}")
    suspend fun deleteById(@Path("id") id: String): Response<Void>
}