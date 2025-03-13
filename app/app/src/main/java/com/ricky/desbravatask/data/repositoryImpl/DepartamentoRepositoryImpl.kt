package com.ricky.desbravatask.data.repositoryImpl

import com.ricky.desbravatask.data.network.api.DepartamentoAPI
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.repository.DepartamentoRepository
import retrofit2.Response
import javax.inject.Inject

class DepartamentoRepositoryImpl @Inject constructor(
    private val api: DepartamentoAPI
) : DepartamentoRepository {
    override suspend fun getAll(userId: String): Response<List<Departamento>> {
        return api.getAll(userId)
    }

    override suspend fun save(model: Departamento): Response<Departamento> {
        return api.save(model)
    }

    override suspend fun getById(id: String): Response<Departamento> {
        return api.getById(id)
    }

    override suspend fun update(model: Departamento): Response<Departamento> {
        return api.update(model)
    }

    override suspend fun delete(id: String): Response<Void> {
        return api.deleteById(id)
    }
}