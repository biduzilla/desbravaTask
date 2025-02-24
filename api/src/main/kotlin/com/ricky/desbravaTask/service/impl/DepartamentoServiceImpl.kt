package com.ricky.desbravaTask.service.impl

import com.ricky.adocao.exception.DepartamentoJaCadastradoException
import com.ricky.adocao.exception.NotFoundException
import com.ricky.desbravaTask.entity.Departamento
import com.ricky.desbravaTask.repository.DepartamentoRepository
import com.ricky.desbravaTask.service.DepartamentoService
import com.ricky.desbravaTask.service.TarefaService
import com.ricky.desbravaTask.utils.I18n
import org.springframework.stereotype.Service

@Service
class DepartamentoServiceImpl(
    private val repository: DepartamentoRepository,
    private val tarefaService: TarefaService,
    private val i18n: I18n
) : DepartamentoService {
    override fun save(entidade: Departamento): Departamento {
        if (repository.existsByNome(entidade.nome)) {
            throw DepartamentoJaCadastradoException()
        }
        return repository.save(entidade)
    }

    override fun findById(id: String): Departamento {
        return repository.findById(id).orElseThrow {
            NotFoundException(i18n.getMessage("error.departamento.nao.encontrado"))
        }
    }

    override fun deleteById(id: String) {
        tarefaService.deleteByIdDepartamento(id)
        repository.deleteById(id)
    }
}