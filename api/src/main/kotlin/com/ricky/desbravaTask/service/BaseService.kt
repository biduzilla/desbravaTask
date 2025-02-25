package com.ricky.desbravaTask.service

interface BaseService<T> {
    fun save(entidade: T): T
    fun findById(id: String): T
    fun deleteById(id: String)
}