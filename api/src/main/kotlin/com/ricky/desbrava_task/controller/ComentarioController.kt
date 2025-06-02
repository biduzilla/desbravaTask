package com.ricky.desbrava_task.controller

import com.ricky.desbrava_task.dto.ComentarioDTO
import com.ricky.desbrava_task.service.ComentarioService
import com.ricky.desbrava_task.utils.CacheConstants
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comentario")
@Tag(
    name = "Comentário",
    description = "Operações relacionadas ao gerenciamento de comentários"
)
class ComentarioController(
    private val comentarioService: ComentarioService
) {
    @GetMapping
    @Cacheable(CacheConstants.COMENTARIOS_CACHE)
    @Operation(
        summary = "Buscar todos os comentários",
        description = "API para buscar todos os comentários com suporte a filtros e paginação."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Comentários encontrados"),
        ]
    )
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(defaultValue = "15") size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ): Page<ComentarioDTO> {
        return comentarioService.findAll(
            search = search,
            qtd = size,
            page = page
        ).map { it.toDTO() }
    }

    @Operation(
        summary = "Buscar comentário por ID",
        description = "API para buscar um comentário específico pelo ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Comentário encontrado"),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ComentarioDTO {
        return comentarioService.findById(id).toDTO()
    }

    @Operation(
        summary = "Criar novo comentário",
        description = "API para criar um novo comentário no sistema."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Comentário criado com sucesso"),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = [CacheConstants.CLUBES_CACHE], allEntries = true)
    fun save(@RequestBody @Valid comentarioDTO: ComentarioDTO): ComentarioDTO {
        return comentarioService.save(comentarioDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Atualizar comentário",
        description = "API para atualizar os dados de um comentário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Comentário atualizado com sucesso"),
        ]
    )
    @PutMapping
    fun update(@RequestBody @Valid comentarioDTO: ComentarioDTO): ComentarioDTO {
        return comentarioService.update(comentarioDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Excluir comentário",
        description = "API para excluir os dados de um comentário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Comentário excluido com sucesso"),
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: String) {
        comentarioService.deleteById(id)
    }
}