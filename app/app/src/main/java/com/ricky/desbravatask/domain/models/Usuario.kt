package com.ricky.desbravatask.domain.models

data class Usuario(
    var id: String = "",
    var name: String = "",
    var senha:String = "",
    var email: String = "",
    var codVerificacao: Int = 0
)
