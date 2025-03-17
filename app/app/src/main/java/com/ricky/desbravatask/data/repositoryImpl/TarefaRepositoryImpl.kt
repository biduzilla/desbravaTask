package com.ricky.desbravatask.data.repositoryImpl

import com.ricky.desbravatask.data.network.api.TarefaAPI
import com.ricky.desbravatask.domain.models.PageModel
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.domain.repository.TarefaRepository
import retrofit2.Response
import javax.inject.Inject

class TarefaRepositoryImpl @Inject constructor(
    private val api: TarefaAPI
) : TarefaRepository {
    override suspend fun getByDepartamento(id: String): Response<List<Tarefa>> =
        api.getByDepartamento(id)

    override suspend fun getAll(search: String, page: Int, size: Int): Response<PageModel<Tarefa>> =
        api.getAll(search, page, size)

    override suspend fun save(model: Tarefa): Response<Tarefa> =
        api.save(model)

    override suspend fun getById(id: String): Response<Tarefa> =
        api.getById(id)

    override suspend fun update(model: Tarefa): Response<Tarefa> =
        api.update(model)

    override suspend fun delete(id: String): Response<Void> =
        api.deleteById(id)
}