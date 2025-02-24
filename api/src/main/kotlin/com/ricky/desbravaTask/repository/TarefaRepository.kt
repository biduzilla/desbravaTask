package com.ricky.desbravaTask.repository

import com.ricky.desbravaTask.entity.Tarefa
import org.springframework.data.jpa.repository.JpaRepository

interface TarefaRepository:JpaRepository<Tarefa,String> {
}