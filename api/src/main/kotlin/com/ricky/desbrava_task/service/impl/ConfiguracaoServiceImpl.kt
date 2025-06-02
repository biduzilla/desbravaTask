package com.ricky.desbrava_task.service.impl

import com.ricky.desbravaTask.enums.ConfiguracaoEnum
import com.ricky.desbrava_task.exceptions.NotFoundException
import com.ricky.desbrava_task.models.Configuracao
import com.ricky.desbrava_task.repository.ConfiguracaoRepository
import com.ricky.desbrava_task.service.ConfiguracaoService
import com.ricky.desbrava_task.utils.I18n
import org.springframework.stereotype.Service

@Service
class ConfiguracaoServiceImpl(
    private val configuracaoRepository: ConfiguracaoRepository,
    private val i18n: I18n
) : ConfiguracaoService {
    override fun findById(id: ConfiguracaoEnum): Configuracao? {
        return configuracaoRepository.findById(id)
            .orElseThrow {
                NotFoundException(i18n.getMessage("error.configuracao.nao.encontrado"))
            }
    }

    override fun findAll(): List<Configuracao> {
        return configuracaoRepository.findAll()
    }

}