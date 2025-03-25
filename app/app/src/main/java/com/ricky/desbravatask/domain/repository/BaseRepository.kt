package com.ricky.desbravatask.domain.repository

import retrofit2.Response

interface Savable<T, U> {
    suspend fun save(model: T): Response<U>
}

interface Fetchable<U> {
    suspend fun getById(id: String): Response<U>
}

interface Updatable<U> {
    suspend fun update(model: U): Response<U>
}

interface Deletable {
    suspend fun delete(id: String): Response<Void>
}

interface BaseRepository<T, U> : Savable<T, U>, Fetchable<U>, Updatable<U>, Deletable