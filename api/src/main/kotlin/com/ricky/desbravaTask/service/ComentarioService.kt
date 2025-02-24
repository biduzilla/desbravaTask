package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.entity.Comentario

interface ComentarioService : BaseService<Comentario> {
    fun findAllByIdTarefa(id: String): List<Comentario>
    fun deleteByIdTarefa(id:String)
    fun deleteByIdDepartamento(id: String)
    fun deleteByIdUsuario(idUsuario: String)
}