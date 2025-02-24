package com.ricky.desbravaTask.repository

import com.ricky.desbravaTask.entity.Comentario
import com.ricky.desbravaTask.entity.Tarefa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ComentarioRepository : JpaRepository<Comentario, String> {

    @Query("select c from Comentario c where c.tarefa.id = :id")
    fun findAllByIdTarefa(@Param("id") id: String): List<Comentario>

    @Modifying
    @Transactional
    @Query("delete from Comentario c where c.tarefa.id = :tarefaId")
    fun deleteByTarefaId(@Param("tarefaId") tarefaId: String)

    @Modifying
    @Transactional
    @Query("""
    delete from Comentario c 
    where c.tarefa.criadoPor.id = :usuarioId 
       or c.tarefa.responsavel.id = :usuarioId
""")
    fun deleteByUsuarioId(@Param("usuarioId") usuarioId: String)

    @Modifying
    @Transactional
    @Query("delete from Comentario c where c.tarefa.departamento.id = :departamentoId")
    fun deleteByDepartamentoId(@Param("departamentoId") departamentoId: String)
}