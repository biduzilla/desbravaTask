package com.ricky.desbravaTask.repository

import com.ricky.desbravaTask.entity.Departamento
import org.springframework.data.jpa.repository.JpaRepository

interface DepartamentoRepository : JpaRepository<Departamento, String> {

    fun existsByNome(nome: String): Boolean
}