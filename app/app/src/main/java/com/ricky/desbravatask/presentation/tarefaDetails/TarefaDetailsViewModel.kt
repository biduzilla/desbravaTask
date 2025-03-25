package com.ricky.desbravatask.presentation.tarefaDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricky.desbravatask.data.local.DataStoreUtil
import com.ricky.desbravatask.domain.usercase.ComentarioManager
import com.ricky.desbravatask.domain.usercase.TarefaManager
import com.ricky.desbravatask.utils.Constants
import com.ricky.desbravatask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TarefaDetailsViewModel @Inject constructor(
    private val tarefaManager: TarefaManager,
    private val savedStateHandle: SavedStateHandle,
    private val dateStoreUtil: DataStoreUtil,
    private val comentarioManager: ComentarioManager
) : ViewModel() {
    private val _state = MutableStateFlow(TarefaDetailsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            dateStoreUtil.getToken()
                .filterNotNull()
                .collect { token ->
                    _state.update { currentState ->
                        currentState.copy(
                            idUser = token.idUser
                        )
                    }
                }
        }

        savedStateHandle.get<String>(Constants.PARAM_TAREFA_ID)?.let { petId ->
            loadTarefa(petId)
        }
    }

    private fun loadComentarios(idTarefa: String) {
        comentarioManager.getByIdTarefa(idTarefa)
            .onEach {result->
                when (result) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message ?: "Error"
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    comentarios = result.data
                                )
                            }
                        }
                    }
                }

            }.launchIn(viewModelScope)
    }


    private fun loadTarefa(idTarefa: String) {
        tarefaManager.getById(idTarefa)
            .onEach {result->
                when (result) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message ?: "Error"
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                        )
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    tarefa = result.data
                                )
                            }
                        }
                    }
                }

            }.launchIn(viewModelScope)
    }

    fun onEvent(event: TarefaDetailsEvent){
        when(event){
            TarefaDetailsEvent.DialogDelete -> {
                _state.update {
                    it.copy(
                        isDialogDelete = _state.value.isDialogDelete
                    )
                }
            }
            TarefaDetailsEvent.DialogEdit -> {
                _state.update {
                    it.copy(
                        isDialogEdit = _state.value.isDialogEdit
                    )
                }
            }
            is TarefaDetailsEvent.OnChangeComentario -> {
                _state.update {
                    it.copy(
                        comentario = event.comentario
                    )
                }
            }
            is TarefaDetailsEvent.OnChangeStatus -> {
                _state.update {
                    it.copy(
                        statusTarefa = event.status
                    )
                }
            }
            TarefaDetailsEvent.SaveComentario -> {

            }
        }
    }
}