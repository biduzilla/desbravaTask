package com.ricky.desbrava_task.repository

import com.ricky.desbrava_task.models.BaseModel
import org.springframework.data.jpa.repository.JpaRepository

interface BaseRepository<T : BaseModel, ID> : JpaRepository<T, ID> {
    override fun delete(entity: T) {
        entity.isExcluido = true
        save(entity)
    }

    override fun deleteById(id: ID & Any) {
        findById(id).ifPresent { delete(it) }
    }

    override fun deleteAll(entities: MutableIterable<T>) {
        saveAll(entities.map { it.apply { isExcluido = true } })
    }
}