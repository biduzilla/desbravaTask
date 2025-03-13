package com.ricky.desbravatask.sample

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.ricky.desbravatask.domain.models.Departamento
import java.time.LocalDateTime

object DepartamentoSample {
    val departamentosSample = listOf(
        Departamento(id = "1", nome = "TI", cor = Color.Cyan.toArgb(), createdAt = LocalDateTime.now(), qtdTarefas = 3),
        Departamento(id = "2", nome = "RH", cor = Color.Green.toArgb(), createdAt = LocalDateTime.now().minusDays(1), qtdTarefas = 7),
        Departamento(id = "3", nome = "Financeiro", cor = Color.Red.toArgb(), createdAt = LocalDateTime.now().minusDays(5), qtdTarefas = 2)

    )

}