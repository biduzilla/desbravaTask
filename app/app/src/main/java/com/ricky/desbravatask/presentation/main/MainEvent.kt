package com.ricky.desbravatask.presentation.main

import com.ricky.desbravatask.domain.models.Departamento

sealed interface MainEvent {
    data class OnChangeDepartamento(private val departamento: Departamento) : MainEvent
    data class OnUpdateDepartamento(private val departamento: Departamento) : MainEvent
   data object AddDepartamento : MainEvent

}