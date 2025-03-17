package com.ricky.desbravatask.domain.repository

import com.ricky.desbravatask.domain.models.PageModel
import com.ricky.desbravatask.domain.models.Tarefa
import retrofit2.Response

interface TarefaRepository : BaseRepository<Tarefa, Tarefa> {
    suspend fun getByDepartamento(id: String): Response<List<Tarefa>>
    suspend fun getAll(
        search: String,
        page: Int,
        size: Int
    ): Response<PageModel<Tarefa>>
}