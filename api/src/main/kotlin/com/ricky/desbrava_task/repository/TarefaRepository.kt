package com.ricky.desbrava_task.repository

import com.ricky.desbrava_task.models.Departamento
import com.ricky.desbrava_task.models.Tarefa
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TarefaRepository:BaseRepository<Tarefa,String> {

    @Query(
        """
        Select t from Tarefa t 
        where :search is null or t.nome like :search%
    """
    )
    fun findAll(@Param("search") search: String?, pageable: Pageable): Page<Tarefa>
}