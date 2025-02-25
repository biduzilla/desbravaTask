package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.entity.Departamento

interface DepartamentoService : BaseService<Departamento> {
    fun findAll(): List<Departamento>
}