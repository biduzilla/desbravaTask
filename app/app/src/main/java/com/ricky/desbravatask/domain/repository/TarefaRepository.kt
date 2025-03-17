package com.ricky.desbravatask.domain.repository

import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.models.PageModel
import com.ricky.desbravatask.domain.models.Tarefa
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface TarefaRepository : BaseRepository<Tarefa, Tarefa> {
    suspend fun getByDepartamento(@Path("id") id: String): Response<List<Tarefa>>
    suspend fun getAll(
        @Query("search") search: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PageModel<Tarefa>>
}