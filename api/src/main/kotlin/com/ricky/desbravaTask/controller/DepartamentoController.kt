package com.ricky.desbravaTask.controller

import com.ricky.desbravaTask.dto.DepartamentoDTO
import com.ricky.desbravaTask.service.DepartamentoService
import com.ricky.desbravaTask.utils.CacheConstants
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/departamentos")
@Tag(
    name = "Departamentos",
    description = "Operações relacionadas ao gerenciamento de departamentos"
)
class DepartamentoController(private val departamentoService: DepartamentoService) {
    @Operation(
        summary = "Obter todos os Departamentos",
        description = "API REST para buscar todos os departamentos"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Status HTTP OK"
            ),
        ]
    )
    @GetMapping
    fun findAll(): List<DepartamentoDTO> {
        return departamentoService.findAll().map { it.toDTO() }
    }

    @Operation(
        summary = "Obter Departamento por ID",
        description = "API REST para buscar um departamento pelo ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Status HTTP OK"
            ),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): DepartamentoDTO {
        return departamentoService.findById(id).toDTO()
    }

    @Operation(
        summary = "Criar Departamento",
        description = "API REST para criar um novo departamento"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Status HTTP CREATED"
            ),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = [CacheConstants.DEPARTAMENTOS_CACHE], allEntries = true)
    fun save(@RequestBody @Valid data: DepartamentoDTO): DepartamentoDTO {
        return departamentoService.save(data.toModel()).toDTO()
    }

    @Operation(
        summary = "Atualizar Departamento",
        description = "API REST para atualizar um departamento existente"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Status HTTP OK"
            ),
        ]
    )
    @PutMapping
    @CacheEvict(value = [CacheConstants.DEPARTAMENTOS_CACHE], allEntries = true)
    fun update(@RequestBody @Valid data: DepartamentoDTO): DepartamentoDTO {
        return departamentoService.update(data.toModel()).toDTO()
    }

    @Operation(
        summary = "Excluir Departamento",
        description = "API REST para excluir um departamento pelo ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Status HTTP NO CONTENT"
            ),
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = [CacheConstants.DEPARTAMENTOS_CACHE], allEntries = true)
    fun deleteById(@PathVariable id: String) {
        departamentoService.deleteById(id)
    }
}