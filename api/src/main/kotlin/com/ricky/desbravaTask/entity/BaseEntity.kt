package com.ricky.desbravaTask.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import lombok.Data
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    @Column(updatable = false)
    @field:CreatedDate
    var createdAt: LocalDateTime? = null,

    @field:CreatedBy
    @Column(updatable = false)
    var createdBy: String = "",

    @field:LastModifiedDate
    @Column(insertable = false)
    var updatedAt: LocalDateTime? = null,

    @field:LastModifiedBy
    @Column(insertable = false)
    var updatedBy: String? = null,
)