package com.ricky.desbravatask.presentation.auth.register

sealed interface RegisterEvent {
    data class OnChangeNome(var nome: String) : RegisterEvent
    data class OnChangeEmail(var email: String) : RegisterEvent
    data class OnChangeSenha(var senha: String) : RegisterEvent
    data class OnChangeConfirmSenha(var confirmacaoSenha: String) : RegisterEvent
    data object OnRegister : RegisterEvent
    data object ClearError : RegisterEvent
}