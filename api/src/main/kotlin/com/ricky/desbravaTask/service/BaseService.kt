package com.ricky.desbravaTask.service

import org.springframework.data.domain.Page

interface BaseService<T> {
    fun save(entidade: T): T
    fun findById(id: String): T
    fun findAll(search: String, qtd: Int, pagina: Int): Page<T>
    fun deleteById(id: String)
}