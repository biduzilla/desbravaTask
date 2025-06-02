package com.ricky.desbrava_task.repository

import com.ricky.desbrava_task.models.Clube
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ClubeRepository : JpaRepository<Clube, String> {
    @Query(
        """
        Select c from Clube c 
        where :search is null or c.nome like :search%
    """
    )
    fun findAll(@Param("search") search: String?, pageable: Pageable): Page<Clube>
}