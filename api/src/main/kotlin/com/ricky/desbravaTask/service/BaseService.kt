package com.ricky.desbravaTask.service

import org.springframework.data.domain.Page

interface BaseService<T> {
    fun save(entidade: T): T
    fun findById(id: String): T
    fun deleteById(id: String)
}