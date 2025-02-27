package com.ricky.desbravaTask.controller

import com.ricky.desbravaTask.dto.TarefaDTO
import com.ricky.desbravaTask.service.TarefaService
import com.ricky.desbravaTask.utils.CacheConstants
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
@RequestMapping("/tarefas")
@Tag(
    name = "Tarefa",
    description = "Operações relacionadas ao gerenciamento de tarefas"
)
class TarefaController(private val tarefaService: TarefaService) {

    @Operation(
        summary = "Create Tarefa",
        description = "REST API to create a new Tarefa"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "HTTP Status CREATED"
            ),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    fun save(@RequestBody @Valid data: TarefaDTO): TarefaDTO {
        return tarefaService.save(data.toModel()).toDTO()
    }

    @GetMapping
    @Cacheable(CacheConstants.USUARIOS_CACHE)
    @Operation(
        summary = "Get all Tarefas",
        description = "REST API to fetch all Tarefas with optional search, pagination, and size filters"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
        ]
    )
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "15") size: Int
    ): Page<TarefaDTO> {
        return tarefaService.findAll(
            search = search,
            qtd = size,
            pagina = page
        ).map { it.toDTO() }
    }

    @Operation(
        summary = "Get Tarefas by Departamento",
        description = "REST API to fetch Tarefas by Departamento ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
        ]
    )
    @GetMapping("/by-departamento/{id}")
    fun findAllByIdDepartamento(@PathVariable id: String): List<TarefaDTO> {
        return tarefaService.findAllByIdDepartamento(id).map { it.toDTO() }
    }

    @Operation(
        summary = "Get Tarefa by ID",
        description = "REST API to fetch a Tarefa by its ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): TarefaDTO {
        return tarefaService.findById(id).toDTO()
    }

    @Operation(
        summary = "Update Tarefa",
        description = "REST API to update an existing Tarefa"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
        ]
    )
    @PutMapping
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    fun update(@RequestBody @Valid data: TarefaDTO): TarefaDTO? {
        return tarefaService.update(data.toModel())?.toDTO()
    }

    @Operation(
        summary = "Delete Tarefa",
        description = "REST API to delete a Tarefa by its ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "HTTP Status OK"
            ),
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    fun deleteById(@PathVariable id: String) {
        tarefaService.deleteById(id)
    }
}