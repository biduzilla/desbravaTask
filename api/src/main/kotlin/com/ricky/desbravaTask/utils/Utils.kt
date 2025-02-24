package com.ricky.desbravaTask.utils

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

fun getPageable(page: Int, size: Int): Pageable {
    val sort: Sort = Sort.by(Sort.Direction.DESC, "createdAt")
    return PageRequest.of(page, size, sort)
}