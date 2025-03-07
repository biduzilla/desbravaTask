package com.ricky.desbravatask.presentation.auth.login

sealed interface LoginEvent {
    data class OnChangeEmail(var email: String) : LoginEvent
    data class OnChangeSenha(var senha: String) : LoginEvent
    data class OnToggleLembrarSenha(var isLembrar: Boolean) : LoginEvent
    data object OnLogin : LoginEvent
    data object ClearError : LoginEvent
}