package com.ricky.desbravatask.presentation.main

import androidx.compose.ui.graphics.Color
import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.domain.models.UsuarioUpdate

data class MainState(
    var error: String = "",
    var userId: String = "",
    var titleDepartamento: String = "",
    var tarefaEnum: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
    var tarefaPrioridade: TarefaPrioridadeEnum = TarefaPrioridadeEnum.BAIXA,
    var nomeDepartamento: String = "",
    var nomeTarefa: String = "",
    var nomeResponsavel: String = "",
    var descricaoTarefa: String = "",
    var corDepartamento: Color? = null,
    var onErrorNomeDepartamento: Boolean = false,
    var onErrorNomeTarefa: Boolean = false,
    var onErrorDescricaoTarefa: Boolean = false,
    var onErrorCorDepartamento: Boolean = false,
    var onErrorResponsavel: Boolean = false,
    var departamentos: List<Departamento> = emptyList(),
    var departamentoTarefa: Departamento = Departamento(),
    var usuarios: List<UsuarioUpdate> = emptyList(),
    var tarefas: List<Tarefa> = emptyList(),
    var tarefa: Tarefa = Tarefa(),
    var departamentoEscolhido: Departamento? = null,
    var responsavelTarefa: UsuarioUpdate? = null,
    var isDialogDepartamento: Boolean = false,
    var isDialogTarefa: Boolean = false,
    var isDialogDeleteDepartamento: Boolean = false,
    var isUpdateDepartamento: Boolean = false,
    var isUpdateTarefa: Boolean = false,
    var isLoading: Boolean = false,
)
