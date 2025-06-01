package com.ricky.desbrava_task.repository

import com.ricky.desbrava_task.models.Comentario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ComentarioRepository : BaseRepository<Comentario, String> {
    @Query(
        """
        Select c from Comentario c 
        where :search is null or c.nome like :comentario%
    """
    )
    fun findAll(@Param("search") search: String?, pageable: Pageable): Page<Comentario>
}