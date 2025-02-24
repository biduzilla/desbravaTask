package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.entity.Tarefa

interface TarefaService : BaseService<Tarefa> {
    fun deleteByIdDepartamento(id: String)
    fun deleteByIdUsuario(idUsuario: String)
}