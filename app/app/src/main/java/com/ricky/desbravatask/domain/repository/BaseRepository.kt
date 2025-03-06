package com.ricky.desbravatask.domain.repository

import retrofit2.Response

interface BaseRepository<T, U> {
    suspend fun save(model: T): Response<U>
    suspend fun getById(id: String): Response<U>
    suspend fun update(model: U): Response<U>
    suspend fun delete(id: String): Response<Void>

}