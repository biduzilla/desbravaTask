package com.ricky.desbrava_task.repository

import com.ricky.desbravaTask.enums.ConfiguracaoEnum
import com.ricky.desbrava_task.models.Configuracao
import org.springframework.data.jpa.repository.JpaRepository

interface ConfiguracaoRepository : JpaRepository<Configuracao, ConfiguracaoEnum> {
}