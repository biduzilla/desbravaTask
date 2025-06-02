package com.ricky.desbrava_task.service

import com.ricky.desbravaTask.enums.ConfiguracaoEnum
import com.ricky.desbrava_task.models.Configuracao

interface ConfiguracaoService {
    fun findById(id: ConfiguracaoEnum): Configuracao?

    fun findAll(): List<Configuracao>
}