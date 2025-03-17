package com.ricky.desbravatask.sample

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import java.time.LocalDateTime

object Exemplos {
    val departamentosSample = listOf(
        Departamento(id = "1", nome = "TI", cor = Color.Cyan.toArgb(), createdAt = LocalDateTime.now(), qtdTarefas = 3),
        Departamento(id = "2", nome = "RH", cor = Color.Green.toArgb(), createdAt = LocalDateTime.now().minusDays(1), qtdTarefas = 7),
        Departamento(id = "3", nome = "Financeiro", cor = Color.Red.toArgb(), createdAt = LocalDateTime.now().minusDays(5), qtdTarefas = 2)

    )

    val tarefaSample = listOf(
        Tarefa(
            id = "1",
            name = "Revisar relatório mensal",
            description = "Revisar e aprovar o relatório mensal do departamento.",
            status = TarefaStatusEnum.EM_ANDAMENTO,
            prioridade = TarefaPrioridadeEnum.ALTA,
            departamento = departamentosSample[0],
            criadoPor = UsuarioUpdate(id = "100", name = "João Silva"),
            responsavel = UsuarioUpdate(id = "101", name = "Maria Souza"),
            createdAt = LocalDateTime.now()
        ),
        Tarefa(
            id = "2",
            name = "Planejar reunião de equipe",
            description = "Organizar e enviar convites para a reunião semanal.",
            status = TarefaStatusEnum.A_FAZER,
            prioridade = TarefaPrioridadeEnum.MEDIA,
            departamento = departamentosSample[1],
            criadoPor = UsuarioUpdate(id = "102", name = "Carlos Oliveira"),
            responsavel = UsuarioUpdate(id = "103", name = "Ana Pereira"),
            createdAt = LocalDateTime.now()
        ),
        Tarefa(
            id = "3",
            name = "Atualizar sistema interno",
            description = "Realizar manutenção e atualizar o sistema ERP.",
            status = TarefaStatusEnum.CONCLUIDA,
            prioridade = TarefaPrioridadeEnum.BAIXA,
            departamento = departamentosSample[2],
            criadoPor = UsuarioUpdate(id = "104", name = "Roberto Lima"),
            responsavel = UsuarioUpdate(id = "105", name = "Fernanda Costa"),
            createdAt = LocalDateTime.now()
        )
    )

}