package com.ricky.desbravaTask.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ErrorView(
    @Schema(description = "Timestamp do erro", example = "2025-02-26T14:30:00", required = true)
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Código de status HTTP", example = "404", required = true)
    val status: Int,

    @Schema(description = "Nome do erro", example = "Not Found", required = true)
    val error: String,

    @Schema(description = "Mensagem de erro detalhada", example = "Resource not found", required = false)
    val message: String?,

    @Schema(description = "Caminho da solicitação que causou o erro", example = "/api/tarefas/123", required = true)
    val path: String
)
