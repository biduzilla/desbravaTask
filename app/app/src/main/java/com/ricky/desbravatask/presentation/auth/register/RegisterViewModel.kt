package com.ricky.desbravatask.presentation.auth.register

import com.ricky.desbravatask.domain.models.Usuario
import com.ricky.desbravatask.domain.usercase.UsuarioManager
import com.ricky.desbravatask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val usuarioManager: UsuarioManager
) {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private fun register() {
        usuarioManager.save(
            Usuario(
                name = _state.value.nome,
                email = _state.value.email,
                senha = _state.value.senha
            )
        ).onEach { result ->
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
                    _state.value = _state.value.copy(
                        isLoading = false,
                        onRegister = true
                    )
                }
            }

        }
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnChangeConfirmSenha -> {
                _state.update {
                    it.copy(
                        confirmacaoSenha = event.confirmacaoSenha,
                        onErrorConfirmacaoSenha = event.confirmacaoSenha.trim().isNotBlank()
                    )
                }
            }

            is RegisterEvent.OnChangeEmail -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        onErrorEmail = event.email.trim().isNotBlank()
                    )
                }
            }

            is RegisterEvent.OnChangeNome -> {
                _state.update {
                    it.copy(
                        nome = event.nome,
                        onErrorNome = event.nome.trim().isNotBlank()
                    )
                }
            }

            is RegisterEvent.OnChangeSenha -> {
                _state.update {
                    it.copy(
                        senha = event.senha,
                        onErrorSenha = event.senha.trim().isNotBlank()
                    )
                }
            }

            RegisterEvent.OnRegister -> {
                if (_state.value.nome.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorNome = true
                        )
                    }
                    return
                }
                if (_state.value.email.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorEmail = true
                        )
                    }
                    return
                }

                if (_state.value.confirmacaoSenha.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorConfirmacaoSenha = true
                        )
                    }
                    return
                }
                if (_state.value.senha.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorSenha = true
                        )
                    }
                    return
                }

                register()
            }

            RegisterEvent.ClearError -> {
                _state.update {
                    it.copy(
                        error = ""
                    )
                }
            }
        }
    }
}