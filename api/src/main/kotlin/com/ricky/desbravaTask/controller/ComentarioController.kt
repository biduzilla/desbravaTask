package com.ricky.desbravaTask.controller

import com.ricky.desbravaTask.dto.ComentarioDTO
import com.ricky.desbravaTask.service.ComentarioService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comentario")
class ComentarioController(private val comentarioService: ComentarioService) {

    @PostMapping
    fun save(@RequestBody @Valid data: ComentarioDTO): ComentarioDTO {
        return comentarioService.save(data.toModel()).toDTO()
    }

    @GetMapping("/by-tarefa/{idTarefa}")
    fun findAllByIdTarefa(@PathVariable idTarefa: String): List<ComentarioDTO> {
        return comentarioService
            .findAllByIdTarefa(idTarefa)
            .map { it.toDTO() }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ComentarioDTO {
        return comentarioService.findById(id).toDTO()
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String) {
        comentarioService.deleteById(id)
    }

}