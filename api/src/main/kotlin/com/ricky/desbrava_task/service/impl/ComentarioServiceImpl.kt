package com.ricky.desbrava_task.service.impl

import com.ricky.desbrava_task.exceptions.NotFoundException
import com.ricky.desbrava_task.models.Comentario
import com.ricky.desbrava_task.repository.ComentarioRepository
import com.ricky.desbrava_task.service.ComentarioService
import com.ricky.desbrava_task.utils.I18n
import com.ricky.desbrava_task.utils.getPageable
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class ComentarioServiceImpl(
    private val comentarioRepository: ComentarioRepository,
    private val i18n: I18n
) : ComentarioService {
    override fun save(entity: Comentario): Comentario {
        return comentarioRepository.save(entity)
    }

    override fun findById(id: String?): Comentario {
        id?.let {
            return comentarioRepository.findById(id)
                .orElseThrow {
                    NotFoundException(i18n.getMessage("error.comentario.nao.encontrado"))
                }
        } ?: throw NotFoundException(i18n.getMessage("error.comentario.nao.encontrado"))
    }

    override fun update(entity: Comentario): Comentario {
        val comentario = findById(entity.idComentario)
        BeanUtils.copyProperties(entity, comentario)
        return save(comentario)
    }

    override fun findAll(search: String?, qtd: Int, page: Int): Page<Comentario> {
        val pageable = getPageable(
            page = page,
            size = qtd
        )

        return comentarioRepository.findAll(search, pageable)
    }

    override fun findAll(): List<Comentario> {
        return comentarioRepository.findAll()
    }

    override fun deleteById(id: String) {
        comentarioRepository.deleteById(id)
    }

}