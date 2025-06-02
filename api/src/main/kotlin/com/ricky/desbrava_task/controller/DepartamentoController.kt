package com.ricky.desbrava_task.controller

import com.ricky.desbrava_task.dto.DepartamentoDTO
import com.ricky.desbrava_task.service.DepartamentoService
import com.ricky.desbrava_task.utils.CacheConstants
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
@RequestMapping("/departamento")
@Tag(
    name = "Departamento",
    description = "Operações relacionadas ao gerenciamento de departamentos"
)
class DepartamentoController(
    private val departamentoService: DepartamentoService
) {
    @GetMapping
    @Cacheable(CacheConstants.DEPARTAMENTOS_CACHE)
    @Operation(
        summary = "Buscar todos os departamentos",
        description = "API para buscar todos os departamentos."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Departamento encontrados"),
        ]
    )
    fun findAll(): List<DepartamentoDTO> {
        return departamentoService.findAll().map { it.toDTO() }
    }

    @Operation(
        summary = "Buscar departamento por ID",
        description = "API para buscar um departamento específico pelo ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Departamento encontrado"),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): DepartamentoDTO {
        return departamentoService.findById(id).toDTO()
    }

    @Operation(
        summary = "Criar novo departamento",
        description = "API para criar um novo usuário no sistema."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Departamento criado com sucesso"),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = [CacheConstants.DEPARTAMENTOS_CACHE], allEntries = true)
    fun save(@RequestBody @Valid departamentoDTO: DepartamentoDTO): DepartamentoDTO {
        return departamentoService.save(departamentoDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Atualizar departamento",
        description = "API para atualizar os dados de um departamento."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso"),
        ]
    )
    @PutMapping
    fun update(@RequestBody @Valid departamentoDTO: DepartamentoDTO): DepartamentoDTO {
        return departamentoService.update(departamentoDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Excluir departamento",
        description = "API para excluir os dados de um usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Departamento excluido com sucesso"),
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: String) {
        departamentoService.deleteById(id)
    }
}