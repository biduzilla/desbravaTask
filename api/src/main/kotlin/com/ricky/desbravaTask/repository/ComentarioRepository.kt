package com.ricky.desbravaTask.repository

import com.ricky.desbravaTask.entity.Comentario
import com.ricky.desbravaTask.entity.Tarefa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ComentarioRepository : JpaRepository<Comentario, String> {

    @Query("select c from Comentario c where c.tarefa.id = :id")
    fun findAllByIdTarefa(@Param("id") id: String): List<Comentario>
}