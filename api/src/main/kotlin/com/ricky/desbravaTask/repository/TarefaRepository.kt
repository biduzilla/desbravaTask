package com.ricky.desbravaTask.repository

import com.ricky.desbravaTask.entity.Tarefa
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface TarefaRepository : JpaRepository<Tarefa, String> {
    @Query(
        "select t from Tarefa t " +
                " where (:search is null or t.name like %:search%) "
    )
    fun findAll(@Param("search") search: String?, pageable: Pageable): Page<Tarefa>

    @Query(
        "select t from Tarefa t" +
                " where t.departamento.id = :idDepartamento"
    )
    fun findAllByIdDepartamento(@Param("idDepartamento") idDepartamento: String): List<Tarefa>

    @Modifying
    @Transactional
    @Query("delete from Tarefa t where t.departamento.id = :departamentoId")
    fun deleteByDepartamentoId(@Param("departamentoId") departamentoId: String)

    @Modifying
    @Transactional
    @Query(
        """
        delete from Tarefa t 
        where t.criadoPor.id = :usuarioId 
           or t.responsavel.id = :usuarioId
    """
    )
    fun deleteByUsuarioId(@Param("usuarioId") usuarioId: String)

    @Query(
        "SELECT t FROM Tarefa t " +
                "WHERE t.departamento.id = :departamentoId " +
                "AND ((t.responsavel.id = :usuarioId AND (t.status = 'A_FAZER' OR t.status = 'EM_ANDAMENTO')) " +
                "OR (t.criadoPor.id = :usuarioId and (t.status = 'EM_REVISAO' OR t.status = 'CONCLUIDA')))"
    )
    fun findByDepartamentoIdAndUsuarioId(
        @Param("departamentoId") departamentoId: String,
        @Param("usuarioId") usuarioId: String
    ): List<Tarefa>
}