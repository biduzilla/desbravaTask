package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.entity.Tarefa
import org.springframework.data.domain.Page
import org.springframework.data.repository.query.Param

interface TarefaService : BaseService<Tarefa> {
    fun findAll(search: String?, qtd: Int, pagina: Int): Page<Tarefa>
    fun findByDepartamentoIdAndUsuarioId(departamentoId: String,usuarioId:String):List<Tarefa>
    fun findAllByIdDepartamento(idDepartamento: String): List<Tarefa>
    fun deleteByIdDepartamento(id: String)
    fun deleteByIdUsuario(idUsuario: String)
}