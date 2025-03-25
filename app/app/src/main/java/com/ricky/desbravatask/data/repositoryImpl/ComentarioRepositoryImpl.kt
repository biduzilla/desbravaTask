package com.ricky.desbravatask.data.repositoryImpl

import com.ricky.desbravatask.data.network.api.ComentarioAPI
import com.ricky.desbravatask.domain.models.Comentario
import com.ricky.desbravatask.domain.repository.ComentarioRepository
import retrofit2.Response
import javax.inject.Inject

class ComentarioRepositoryImpl @Inject constructor(private val api: ComentarioAPI) :
    ComentarioRepository {
    override suspend fun getByIdTarefa(idTarefa: String): Response<List<Comentario>> {
        return api.getByIdTarefa(idTarefa)
    }

    override suspend fun save(model: Comentario): Response<Comentario> {
        return api.save(model)
    }

    override suspend fun getById(id: String): Response<Comentario> {
        return api.getById(id)
    }

    override suspend fun update(model: Comentario): Response<Comentario> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String): Response<Void> {
        return api.deleteById(id)
    }
}