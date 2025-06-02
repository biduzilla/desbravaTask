package com.ricky.desbrava_task.service.impl

import com.ricky.desbrava_task.exceptions.DesbravaTaskErrorException
import com.ricky.desbrava_task.exceptions.NotFoundException
import com.ricky.desbrava_task.models.Tarefa
import com.ricky.desbrava_task.repository.TarefaRepository
import com.ricky.desbrava_task.service.TarefaService
import com.ricky.desbrava_task.utils.I18n
import com.ricky.desbrava_task.utils.getPageable
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class TarefaServiceImpl(
    private val tarefaRepository: TarefaRepository,
    private val i18n: I18n
) : TarefaService {
    override fun save(entity: Tarefa): Tarefa {
        return tarefaRepository.save(entity)
    }

    override fun findById(id: String): Tarefa? {
        return tarefaRepository.findById(id)
            .orElseThrow {
                NotFoundException(i18n.getMessage("error.tarefa.nao.encontrado"))
            }
    }

    override fun findAll(search: String?, qtd: Int, page: Int): Page<Tarefa> {
        val pageable = getPageable(
            page = page,
            size = qtd
        )
        return tarefaRepository.findAll(search, pageable)
    }

    override fun findAll(): List<Tarefa> {
        return tarefaRepository.findAll()
    }

    override fun deleteById(id: String) {
        return tarefaRepository.deleteById(id)
    }
}