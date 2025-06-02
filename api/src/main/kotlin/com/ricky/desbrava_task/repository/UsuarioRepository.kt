package com.ricky.desbrava_task.repository

import com.ricky.desbrava_task.models.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UsuarioRepository : JpaRepository<Usuario, String> {

    @Query(
        """
        Select u from Usuario u 
        where 
            :search is null 
            or u.nome like :search% or u.email like :search%
    """
    )
    fun findAll(@Param("search") search: String?, pageable: Pageable): Page<Usuario>
}