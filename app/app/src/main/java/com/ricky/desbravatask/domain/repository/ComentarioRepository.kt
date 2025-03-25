package com.ricky.desbravatask.domain.repository

import com.ricky.desbravatask.domain.models.Comentario
import retrofit2.Response

interface ComentarioRepository:BaseRepository<Comentario,Comentario> {
    suspend fun getByIdTarefa(idTarefa: String): Response<List<Comentario>>
}