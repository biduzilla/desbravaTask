package com.ricky.desbravaTask.service.impl

import com.ricky.desbravaTask.entity.Configuracao
import com.ricky.desbravaTask.enums.ConfiguracaoEnum
import com.ricky.desbravaTask.exceptions.NotFoundException
import com.ricky.desbravaTask.repository.ConfiguracaoRepository
import com.ricky.desbravaTask.service.ConfiguracaoService
import com.ricky.desbravaTask.utils.I18n
import org.springframework.stereotype.Service

@Service
class ConfiguracaoServiceImpl(
    private val repository: ConfiguracaoRepository,
    private val i18n: I18n
):ConfiguracaoService {
    override fun findById(codConfiguracao: ConfiguracaoEnum): Configuracao {
        return repository.findById(codConfiguracao)
            .orElseThrow { NotFoundException(i18n.getMessage("error.configuracao.nao.encontrado")) }
    }

    override fun findAll(): List<Configuracao> {
        return repository.findAll()
    }
}