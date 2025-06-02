package com.ricky.desbrava_task.controller

import com.ricky.desbrava_task.dto.TarefaDTO
import com.ricky.desbrava_task.service.TarefaService
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
@RequestMapping("/tarefa")
@Tag(
    name = "Tarefa",
    description = "Operações relacionadas ao gerenciamento de tarefas"
)
class TarefaController(
    private val tarefaService: TarefaService
) {
    @GetMapping
    @Cacheable(CacheConstants.TAREFAS_CACHE)
    @Operation(
        summary = "Buscar todos os tarefas",
        description = "API para buscar todos os tarefas com suporte a filtros e paginação."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tarefas encontrados"),
        ]
    )
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(defaultValue = "15") size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ): Page<TarefaDTO> {
        return tarefaService.findAll(
            search = search,
            qtd = size,
            page = page
        ).map { it.toDTO() }
    }

    @Operation(
        summary = "Buscar tarefa por ID",
        description = "API para buscar tarefa específica pelo ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tarefa encontrado"),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): TarefaDTO {
        return tarefaService.findById(id).toDTO()
    }

    @Operation(
        summary = "Criar novo tarefa",
        description = "API para criar uma nova tarefa no sistema."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Tarefa criado com sucesso"),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = [CacheConstants.DEPARTAMENTOS_CACHE], allEntries = true)
    fun save(@RequestBody @Valid tarefaDTO: TarefaDTO): TarefaDTO {
        return tarefaService.save(tarefaDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Atualizar tarefa",
        description = "API para atualizar os dados de uma tarefa."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tarefa atualizado com sucesso"),
        ]
    )
    @PutMapping
    fun update(@RequestBody @Valid tarefaDTO: TarefaDTO): TarefaDTO {
        return tarefaService.update(tarefaDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Excluir tarefa",
        description = "API para excluir os dados de uma tarefa."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tarefa excluida com sucesso"),
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: String) {
        tarefaService.deleteById(id)
    }
}