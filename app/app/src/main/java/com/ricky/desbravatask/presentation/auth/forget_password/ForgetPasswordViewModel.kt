package com.ricky.desbravatask.presentation.auth.forget_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricky.desbravatask.domain.models.ResetSenha
import com.ricky.desbravatask.domain.usercase.UsuarioManager
import com.ricky.desbravatask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(private val userManager: UsuarioManager) :
    ViewModel() {
    private val _state = MutableStateFlow(ForgetPasswordState())
    val state = _state.asStateFlow()

    fun onEvent(event: ForgetPasswordEvent) {
        when (event) {
            is ForgetPasswordEvent.OnChangeCod -> {
                _state.update {
                    it.copy(
                        cod = event.cod,
                        onErrorCod = false
                    )
                }
            }

            is ForgetPasswordEvent.OnChangeConfirmSenha -> {
                _state.update {
                    it.copy(
                        confirmSenha = event.senha,
                        onErrorConfirmSenha = false
                    )
                }
            }

            is ForgetPasswordEvent.OnChangeEmail -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        onErrorEmail = false
                    )
                }
            }

            is ForgetPasswordEvent.OnChangeSenha -> {
                _state.update {
                    it.copy(
                        senha = event.senha,
                        onErrorSenha = false
                    )
                }
            }

            ForgetPasswordEvent.OnSendCod -> {
                if (_state.value.cod.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorCod = true
                        )
                    }
                    return
                }

                userManager.verificarCod(
                    cod = _state.value.cod.toInt(),
                    email = _state.value.email
                ).onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    error = result.message ?: "",
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isCodVer = true
                                )
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }

            ForgetPasswordEvent.OnSendEmail -> {
                if (_state.value.email.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorEmail = true
                        )
                    }
                    return
                }
                userManager.resetSenha(_state.value.email)
                    .onEach { result ->
                        when (result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message ?: "",
                                        isLoading = false
                                    )
                                }
                            }

                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        isLoading = true
                                    )
                                }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isEmailSend = true
                                    )
                                }
                            }
                        }
                    }.launchIn(viewModelScope)
            }

            ForgetPasswordEvent.OnUpdatePassword -> {
                if (_state.value.senha.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorSenha = true
                        )
                    }
                    return
                }

                if (_state.value.confirmSenha.trim()
                        .isBlank() || _state.value.senha != _state.value.confirmSenha
                ) {
                    _state.update {
                        it.copy(
                            onErrorConfirmSenha = true
                        )
                    }
                    return
                }

                val resetSenha = ResetSenha(
                    email = _state.value.email,
                    senha = _state.value.senha,
                    cod = _state.value.cod.toInt()
                )

                userManager.alterarSenha(resetSenha).onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    error = result.message ?: "",
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isOk = true
                                )
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }

            ForgetPasswordEvent.ClearError -> {
                _state.update {
                    it.copy(
                        error = ""
                    )
                }
            }
        }
    }
}