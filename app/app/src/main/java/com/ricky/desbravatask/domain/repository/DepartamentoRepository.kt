package com.ricky.desbravatask.domain.repository

import com.ricky.desbravatask.domain.models.Departamento
import retrofit2.Response

interface DepartamentoRepository : BaseRepository<Departamento, Departamento> {
    suspend fun getAll(userId:String): Response<List<Departamento>>
}