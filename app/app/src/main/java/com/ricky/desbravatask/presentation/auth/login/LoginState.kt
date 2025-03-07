package com.ricky.desbravatask.presentation.auth.login

data class LoginState(
    var isLoading: Boolean = false,
    var email: String = "",
    var senha: String = "",
    var onErrorEmail: Boolean = false,
    var onErrorSenha: Boolean = false,
)