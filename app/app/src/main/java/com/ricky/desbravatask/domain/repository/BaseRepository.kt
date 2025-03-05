package com.ricky.desbravatask.domain.repository

import com.ricky.desbravatask.domain.models.Usuario
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

interface BaseRepository<T, U> {
    suspend fun save(model: T): Response<U>
    suspend fun getById(id: String): Response<U>
    suspend fun update(model: U): Response<U>
    suspend fun delete(id: String): Response<Void>

}