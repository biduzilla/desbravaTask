package com.ricky.desbravaTask.controller

import com.ricky.desbravaTask.dto.ComentarioDTO
import com.ricky.desbravaTask.service.ComentarioService
import com.ricky.desbravaTask.utils.CacheConstants
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comentario")
@Tag(
    name = "Comentário",
    description = "Operações relacionadas ao gerenciamento de comentários"
)
class ComentarioController(private val comentarioService: ComentarioService) {

    @PostMapping
    @CacheEvict(value = [CacheConstants.COMENTARIOS_CACHE], allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Criar Comentário",
        description = "API REST para criar um novo comentário"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Status HTTP CREATED"
            ),
        ]
    )
    fun save(@RequestBody @Valid data: ComentarioDTO): ComentarioDTO {
        return comentarioService.save(data.toModel()).toDTO()
    }

    @GetMapping("/by-tarefa/{idTarefa}")
    @Cacheable(CacheConstants.COMENTARIOS_CACHE)
    @Operation(
        summary = "Obter Comentários por Tarefa",
        description = "API REST para buscar todos os comentários associados a uma tarefa específica"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Status HTTP OK"
            ),
        ]
    )
    fun findAllByIdTarefa(@PathVariable idTarefa: String): List<ComentarioDTO> {
        return comentarioService
            .findAllByIdTarefa(idTarefa)
            .map { it.toDTO() }
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obter Comentário por ID",
        description = "API REST para buscar um comentário pelo seu ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Status HTTP OK"
            ),
        ]
    )
    fun findById(@PathVariable id: String): ComentarioDTO {
        return comentarioService.findById(id).toDTO()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Excluir Comentário",
        description = "API REST para excluir um comentário pelo ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Status HTTP NO CONTENT"
            ),
        ]
    )
    fun deleteById(@PathVariable id: String) {
        comentarioService.deleteById(id)
    }

}