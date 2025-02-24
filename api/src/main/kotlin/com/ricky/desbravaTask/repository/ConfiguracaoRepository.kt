package com.ricky.desbravaTask.repository

import com.ricky.desbravaTask.entity.Configuracao
import com.ricky.desbravaTask.enums.ConfiguracaoEnum
import org.springframework.data.jpa.repository.JpaRepository

interface ConfiguracaoRepository : JpaRepository<Configuracao, ConfiguracaoEnum> {
}