package com.ricky.desbravaTask.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "USUARIO")
data class Usuario(
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,
    @Column(name = "NAME", length = 20)
    var name: String = "",
    @Column(name = "EMAIL", length = 50)
    var email: String = "",
    @Column(name = "SENHA", length = 250)
    var senha: String = ""
) : BaseEntity()
