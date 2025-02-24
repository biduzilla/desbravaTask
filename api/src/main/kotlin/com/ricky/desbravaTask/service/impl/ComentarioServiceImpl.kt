package com.ricky.desbravaTask.service.impl

import com.ricky.adocao.exception.NotFoundException
import com.ricky.desbravaTask.entity.Comentario
import com.ricky.desbravaTask.repository.ComentarioRepository
import com.ricky.desbravaTask.service.ComentarioService
import org.springframework.stereotype.Service

@Service
class ComentarioServiceImpl(private val repository: ComentarioRepository) : ComentarioService {
    override fun save(entidade: Comentario): Comentario {
        return repository.save(entidade)
    }

    override fun findAllByIdTarefa(id: String): List<Comentario> {
        return repository.findAllByIdTarefa(id)
    }

    override fun deleteByTarefa(id: String) {
        repository.deleteByTarefaId(id)
    }

    override fun findById(id: String): Comentario {
        return repository.findById(id)
            .orElseThrow {
                NotFoundException("error.comentario.nao.encontrado")
            }
    }

    override fun deleteById(id: String) {
        repository.deleteById(id)
    }
}