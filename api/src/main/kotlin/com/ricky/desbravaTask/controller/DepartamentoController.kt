package com.ricky.desbravaTask.controller

import com.ricky.desbravaTask.dto.DepartamentoDTO
import com.ricky.desbravaTask.service.DepartamentoService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/departamentos")
class DepartamentoController(private val departamentoService: DepartamentoService) {
    @GetMapping
    fun findAll(): List<DepartamentoDTO> {
        return departamentoService.findAll().map { it.toDTO() }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): DepartamentoDTO {
        return departamentoService.findById(id).toDTO()
    }

    @PostMapping
    fun save(@RequestBody @Valid data: DepartamentoDTO): DepartamentoDTO {
        return departamentoService.save(data.toModel()).toDTO()
    }

    @PutMapping
    fun update(@RequestBody @Valid data: DepartamentoDTO): DepartamentoDTO {
        return departamentoService.save(data.toModel()).toDTO()
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String) {
        departamentoService.deleteById(id)
    }
}