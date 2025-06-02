package com.ricky.desbrava_task.service.impl

import com.ricky.desbrava_task.exceptions.DesbravaTaskErrorException
import com.ricky.desbrava_task.exceptions.NotFoundException
import com.ricky.desbrava_task.models.Usuario
import com.ricky.desbrava_task.repository.UsuarioRepository
import com.ricky.desbrava_task.service.UsuarioService
import com.ricky.desbrava_task.utils.I18n
import com.ricky.desbrava_task.utils.getPageable
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository,
    private val i18n: I18n
) : UsuarioService {
    override fun save(entity: Usuario): Usuario {
        return usuarioRepository.save(entity)
    }

    override fun findById(id: String): Usuario? {
        return usuarioRepository.findById(id)
            .orElseThrow {
                NotFoundException(i18n.getMessage("error.usuario.nao.encontrado"))
            }
    }

    override fun findAll(search: String?, qtd: Int, page: Int): Page<Usuario> {
        val pageable = getPageable(
            page = page,
            size = qtd
        )
        return usuarioRepository.findAll(search, pageable)
    }

    override fun findAll(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    override fun deleteById(id: String) {
        usuarioRepository.deleteById(id)
    }
}