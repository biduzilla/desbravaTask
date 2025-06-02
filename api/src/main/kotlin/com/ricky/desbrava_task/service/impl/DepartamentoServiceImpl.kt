package com.ricky.desbrava_task.service.impl

import com.ricky.desbrava_task.exceptions.DesbravaTaskErrorException
import com.ricky.desbrava_task.exceptions.NotFoundException
import com.ricky.desbrava_task.models.Departamento
import com.ricky.desbrava_task.repository.DepartamentoRepository
import com.ricky.desbrava_task.service.DepartamentoService
import com.ricky.desbrava_task.utils.I18n
import com.ricky.desbrava_task.utils.getPageable
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class DepartamentoServiceImpl(
    private val departamentoRepository: DepartamentoRepository,
    private val i18n: I18n
) : DepartamentoService {
    override fun save(entity: Departamento): Departamento {
        return departamentoRepository.save(entity)
    }

    override fun findById(id: String): Departamento? {
        return departamentoRepository.findById(id)
            .orElseThrow {
                NotFoundException(i18n.getMessage("error.departamento.nao.encontrado"))
            }
    }

    override fun findAll(search: String?, qtd: Int, page: Int): Page<Departamento> {
        val pageable = getPageable(
            page = page,
            size = qtd
        )

        return departamentoRepository.findAll(search, pageable)
    }

    override fun findAll(): List<Departamento> {
        return departamentoRepository.findAll()
    }

    override fun deleteById(id: String) {
        departamentoRepository.deleteById(id)
    }

}