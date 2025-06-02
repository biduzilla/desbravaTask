package com.ricky.desbrava_task.service

import org.springframework.data.domain.Page

interface BaseService<T> {
    fun save(entity: T): T
    fun findById(id: String?): T
    fun update(entity: T): T
    fun findAll(search: String?, qtd: Int, page: Int): Page<T>
    fun findAll(): List<T>
    fun deleteById(id: String)
}