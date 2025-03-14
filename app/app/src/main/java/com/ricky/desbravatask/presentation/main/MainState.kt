package com.ricky.desbravatask.presentation.main

import androidx.compose.ui.graphics.Color
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import com.ricky.desbravatask.domain.models.Departamento

data class MainState(
    var error: String = "",
    var userId: String = "",
    var titleDepartamento: String = "",
    var tarefaEnum: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
    var nomeDepartamento: String = "",
    var corDepartamento: Color? = null,
    var onErrorNomeDepartamento: Boolean = false,
    var onErrorCorDepartamento: Boolean = false,
    var departamentos: List<Departamento> = emptyList(),
    var departamentoEscolhido: Departamento? = null,
    var isDialogDepartamento: Boolean = false,
    var isDialogDeleteDepartamento: Boolean = false,
    var isUpdateDepartamento: Boolean = false,
    var isLoading: Boolean = false,
)
