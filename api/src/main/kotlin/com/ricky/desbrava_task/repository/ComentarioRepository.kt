package com.ricky.desbrava_task.repository

import com.ricky.desbrava_task.models.Comentario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ComentarioRepository : JpaRepository<Comentario, String> {
    @Query(
        """
        Select c from Comentario c 
        where :search is null or c.comentario like :comentario%
    """
    )
    fun findAll(@Param("search") search: String?, pageable: Pageable): Page<Comentario>
}