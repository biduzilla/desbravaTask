package com.ricky.desbravaTask.service.impl

import com.ricky.adocao.exception.EmailJaCadastradoException
import com.ricky.adocao.exception.NotFoundException
import com.ricky.adocao.exception.SenhaCurtaException
import com.ricky.desbravaTask.entity.Usuario
import com.ricky.desbravaTask.repository.UsuarioRepository
import com.ricky.desbravaTask.service.TarefaService
import com.ricky.desbravaTask.service.UsuarioService
import com.ricky.desbravaTask.utils.I18n
import com.ricky.desbravaTask.utils.getPageable
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class UsuarioServiceImpl(
    private val repository: UsuarioRepository,
    private val i18n: I18n,
    private val tarefaService: TarefaService
) : UsuarioService {
    override fun save(entidade: Usuario): Usuario {
        if (repository.existsByEmail(entidade.email)) {
            throw EmailJaCadastradoException(i18n.getMessage("error.email.cadastrado"))
        }
        verificarSenha(entidade.senha)
        return repository.save(entidade)
    }

    override fun findById(id: String): Usuario {
        return repository
            .findById(id)
            .orElseThrow { NotFoundException(i18n.getMessage("error.usuario.nao.encontrado")) }
    }

    override fun findAll(search: String?, qtd: Int, pagina: Int): Page<Usuario> {
        val pageable = getPageable(
            page = pagina,
            size = qtd
        )
        return repository.findAll(
            search = search,
            pageable = pageable
        )
    }

    override fun deleteById(id: String) {
        tarefaService.deleteByIdUsuario(id)
        repository.deleteById(id)
    }

    private fun verificarSenha(senha: String) {
        if (senha.toCharArray().size <= 7) {
            throw SenhaCurtaException(i18n.getMessage("error.senha.curta"))
        }
    }
}