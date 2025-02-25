package com.ricky.desbravaTask.controller

import com.ricky.desbravaTask.dto.TarefaDTO
import com.ricky.desbravaTask.service.TarefaService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tarefas")
class TarefaController(private val tarefaService: TarefaService) {
    @PostMapping
    fun save(@RequestBody @Valid data: TarefaDTO): TarefaDTO {
        return tarefaService.save(data.toModel()).toDTO()
    }

    @GetMapping
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

    @GetMapping("/by-departamento/{id}")
    fun findAllByIdDepartamento(@PathVariable id: String): List<TarefaDTO> {
        return tarefaService.findAllByIdDepartamento(id).map { it.toDTO() }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): TarefaDTO {
        return tarefaService.findById(id).toDTO()
    }

    @PutMapping
    fun update(@RequestBody @Valid data: TarefaDTO): TarefaDTO {
        return tarefaService.save(data.toModel()).toDTO()
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String) {
        tarefaService.deleteById(id)
    }
}