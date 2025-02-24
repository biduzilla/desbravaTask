package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.entity.Comentario

interface ComentarioService : BaseService<Comentario> {
    fun findAllByIdTarefa(id: String): List<Comentario>
    fun deleteByTarefa(id:String)
}