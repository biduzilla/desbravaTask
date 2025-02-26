package com.ricky.desbravaTask.dto

import io.swagger.v3.oas.annotations.media.Schema

data class TokenDTO(
    @Schema(description = "Token gerado para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    var token: String = "",

    @Schema(description = "ID do usuário associado ao token", example = "12345")
    var idUser: String = "",

    @Schema(description = "Nome do usuário associado ao token", example = "João Silva")
    var nome: String = ""
)
