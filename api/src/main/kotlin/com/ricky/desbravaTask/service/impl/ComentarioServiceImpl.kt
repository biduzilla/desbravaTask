package com.ricky.desbravaTask.service.impl

import com.ricky.desbravaTask.exceptions.NotFoundException
import com.ricky.desbravaTask.entity.Comentario
import com.ricky.desbravaTask.repository.ComentarioRepository
import com.ricky.desbravaTask.service.ComentarioService
import com.ricky.desbravaTask.utils.I18n
import org.springframework.stereotype.Service

@Service
class ComentarioServiceImpl(
    private val repository: ComentarioRepository,
    private val i18n: I18n
) : ComentarioService {
    override fun save(entidade: Comentario): Comentario {
        return repository.save(entidade)
    }

    override fun findAllByIdTarefa(id: String): List<Comentario> {
        return repository.findAllByIdTarefa(id)
    }

    override fun deleteByIdTarefa(id: String) {
        repository.deleteByTarefaId(id)
    }

    override fun findById(id: String): Comentario {
        return repository.findById(id)
            .orElseThrow {
                NotFoundException(i18n.getMessage("error.comentario.nao.encontrado"))
            }
    }

    override fun deleteById(id: String) {
        repository.deleteById(id)
    }

    override fun deleteByIdDepartamento(id: String) {
        repository.deleteByDepartamentoId(id)
    }

    override fun deleteByIdUsuario(idUsuario: String) {
        repository.deleteById(idUsuario)
    }
}