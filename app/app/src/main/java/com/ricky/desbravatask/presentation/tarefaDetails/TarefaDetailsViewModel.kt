package com.ricky.desbravatask.presentation.tarefaDetails

import androidx.lifecycle.SavedStateHandle
import com.ricky.desbravatask.data.local.DataStoreUtil
import com.ricky.desbravatask.domain.usercase.TarefaManager
import javax.inject.Inject

class TarefaDetailsViewModel @Inject constructor(
    private val tarefaManager: TarefaManager,
    private val savedStateHandle: SavedStateHandle,
    private val dateStoreUtil: DataStoreUtil
) {
}