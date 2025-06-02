package com.ricky.desbrava_task.controller

import com.ricky.desbrava_task.dto.ClubeDTO
import com.ricky.desbrava_task.service.ClubeService
import com.ricky.desbrava_task.utils.CacheConstants
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clube")
@Tag(
    name = "Clube",
    description = "Operações relacionadas ao gerenciamento de clubes"
)
class ClubeController(
    private val clubeService: ClubeService
) {
    @GetMapping
    @Cacheable(CacheConstants.CLUBES_CACHE)
    @Operation(
        summary = "Buscar todos os usuários",
        description = "API para buscar todos os clubes com suporte a filtros e paginação."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Clubes encontrados"),
        ]
    )
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(defaultValue = "15") size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ): Page<ClubeDTO> {
        return clubeService.findAll(
            search = search,
            qtd = size,
            page = page
        ).map { it.toDTO() }
    }

    @Operation(
        summary = "Buscar clubes por ID",
        description = "API para buscar um clube específico pelo ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Clube encontrado"),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ClubeDTO? {
        return clubeService.findById(id)?.toDTO()
    }

    @Operation(
        summary = "Criar novo clube",
        description = "API para criar um novo clube no sistema."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Clube criado com sucesso"),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = [CacheConstants.CLUBES_CACHE], allEntries = true)
    fun save(@RequestBody clubeDTO: ClubeDTO): ClubeDTO {
        return clubeService.save(clubeDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Atualizar clube",
        description = "API para atualizar os dados de um clube."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Clube atualizado com sucesso"),
        ]
    )
    @PutMapping
    fun update(@RequestBody clubeDTO: ClubeDTO): ClubeDTO {
        return clubeService.update(clubeDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Excluir clube",
        description = "API para excluir os dados de um clube."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Clube excluido com sucesso"),
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: String) {
        clubeService.deleteById(id)
    }
}