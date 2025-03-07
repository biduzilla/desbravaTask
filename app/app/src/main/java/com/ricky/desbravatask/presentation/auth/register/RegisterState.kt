package com.ricky.desbravatask.presentation.auth.register

data class RegisterState(
    var error: String = "",
    var nome: String = "",
    var email: String = "",
    var senha: String = "",
    var confirmacaoSenha: String = "",
    var onErrorNome: Boolean = false,
    var onErrorEmail: Boolean = false,
    var onErrorSenha: Boolean = false,
    var onErrorConfirmacaoSenha: Boolean = false,
    var isLoading: Boolean = false,
    var onRegister: Boolean = false,
    var isUpdate: Boolean = false,
    )
