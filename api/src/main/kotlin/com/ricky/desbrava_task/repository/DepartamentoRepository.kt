package com.ricky.desbrava_task.repository

import com.ricky.desbrava_task.models.Clube
import com.ricky.desbrava_task.models.Departamento
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DepartamentoRepository : BaseRepository<Departamento, String> {
    @Query(
        """
        Select d from Departamento d 
        where :search is null or d.nome like :search%
    """
    )
    fun findAll(@Param("search") search: String?, pageable: Pageable): Page<Departamento>
}