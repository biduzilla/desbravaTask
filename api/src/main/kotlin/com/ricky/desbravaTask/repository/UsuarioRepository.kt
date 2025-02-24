package com.ricky.desbravaTask.repository

import com.ricky.desbravaTask.entity.Usuario
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UsuarioRepository : JpaRepository<Usuario, String> {
    fun existsByEmail(email: String): Boolean

    @Query("select a from Usuario a" +
            "where (:search is null or a.email like %:search% or a.name like %:pesquisa%) ")
    fun findAll(@Param("search")search: String?, pageable: Pageable): Page<Usuario>
}