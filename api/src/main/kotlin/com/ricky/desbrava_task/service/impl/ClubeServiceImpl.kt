package com.ricky.desbrava_task.service.impl

import com.ricky.desbrava_task.exceptions.NotFoundException
import com.ricky.desbrava_task.models.Clube
import com.ricky.desbrava_task.repository.ClubeRepository
import com.ricky.desbrava_task.service.ClubeService
import com.ricky.desbrava_task.utils.I18n
import com.ricky.desbrava_task.utils.getPageable
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class ClubeServiceImpl(
    private val clubeRepository: ClubeRepository,
    private val i18n: I18n
) : ClubeService {
    override fun save(entity: Clube): Clube {
        return clubeRepository.save(entity)
    }

    override fun findById(id: String?): Clube {
        id?.let {
            return clubeRepository.findById(id)
                .orElseThrow {
                    NotFoundException(i18n.getMessage("error.clube.nao.encontrado"))
                }
        } ?: throw NotFoundException(i18n.getMessage("error.clube.nao.encontrado"))
    }

    override fun update(entity: Clube): Clube {
        val clube = findById(entity.idClube)
        BeanUtils.copyProperties(entity, clube)
        return save(clube)
    }

    override fun findAll(search: String?, qtd: Int, page: Int): Page<Clube> {
        val pageable = getPageable(
            page = page,
            size = qtd
        )
        return clubeRepository.findAll(search, pageable)
    }

    override fun findAll(): List<Clube> {
        return clubeRepository.findAll()
    }

    override fun deleteById(id: String) {
        clubeRepository.deleteById(id)
    }
}