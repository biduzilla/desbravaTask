package com.ricky.desbravaTask.service.impl

import com.ricky.desbravaTask.entity.Tarefa
import com.ricky.desbravaTask.exceptions.NotFoundException
import com.ricky.desbravaTask.repository.TarefaRepository
import com.ricky.desbravaTask.service.ComentarioService
import com.ricky.desbravaTask.service.TarefaService
import com.ricky.desbravaTask.service.UsuarioService
import com.ricky.desbravaTask.utils.I18n
import com.ricky.desbravaTask.utils.getPageable
import org.springframework.beans.BeanUtils
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class TarefaServiceImpl(
    private val repository: TarefaRepository,
    private val comentarioService: ComentarioService,
    @Lazy private val usuarioService: UsuarioService,
    private val i18n: I18n
) : TarefaService {

    override fun save(entidade: Tarefa): Tarefa {
        entidade.responsavel?.id?.let {
            entidade.responsavel = usuarioService.findById(it)
        }
        entidade.criadoPor?.id?.let {
            entidade.criadoPor = usuarioService.findById(it)
        }

        return repository.save(entidade)
    }

    override fun update(entitade: Tarefa): Tarefa {
        val data = findById(entitade.id)
        BeanUtils.copyProperties(entitade, data)
        return repository.save(data)
    }

    override fun findAll(search: String?, qtd: Int, pagina: Int): Page<Tarefa> {
        val pageable = getPageable(
            page = pagina,
            size = qtd
        )
        return repository.findAll(search, pageable)
    }

    override fun findAllByIdDepartamento(idDepartamento: String): List<Tarefa> {
        return repository.findAllByIdDepartamento(idDepartamento)
    }

    override fun findById(id: String): Tarefa {
        return repository.findById(id).orElseThrow {
            NotFoundException(i18n.getMessage("error.tarefa.nao.encontrado"))
        }
    }

    override fun deleteById(id: String) {
        comentarioService.deleteByIdTarefa(id)
        repository.deleteById(id)
    }

    override fun deleteByIdDepartamento(id: String) {
        comentarioService.deleteByIdDepartamento(id)
        repository.deleteByDepartamentoId(id)
    }

    override fun deleteByIdUsuario(idUsuario: String) {
        comentarioService.deleteByIdUsuario(idUsuario)
        repository.deleteByUsuarioId(idUsuario)
    }
}