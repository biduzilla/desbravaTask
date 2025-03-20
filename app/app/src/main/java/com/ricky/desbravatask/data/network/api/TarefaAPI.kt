package com.ricky.desbravatask.data.network.api

import com.ricky.desbravatask.domain.models.PageModel
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TarefaAPI {
    @POST(Constants.TAREFA_ENDPOINT)
    suspend fun save(@Body tarefa: Tarefa): Response<Tarefa>

    @GET(Constants.TAREFA_ENDPOINT)
    suspend fun getAll(
        @Query("search") search: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PageModel<Tarefa>>

    @GET("${Constants.TAREFA_ENDPOINT}/{id}")
    suspend fun getById(@Path("id") id: String): Response<Tarefa>

    @GET("${Constants.TAREFA_BY_DEPARTAMENTO}/{id}")
    suspend fun getByDepartamento(@Path("id") id: String): Response<List<Tarefa>>

    @PUT(Constants.TAREFA_ENDPOINT)
    suspend fun update(@Body tarefa: Tarefa): Response<Tarefa>

    @DELETE("${Constants.TAREFA_ENDPOINT}/{id}")
    suspend fun deleteById(@Path("id") id: String): Response<Void>
}