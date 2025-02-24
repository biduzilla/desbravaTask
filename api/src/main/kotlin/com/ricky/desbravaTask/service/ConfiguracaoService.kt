package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.entity.Configuracao
import com.ricky.desbravaTask.enums.ConfiguracaoEnum

interface ConfiguracaoService {
    fun findById(codConfiguracao: ConfiguracaoEnum): Configuracao
    fun findAll(): List<Configuracao>
}