package com.ricky.desbravaTask.exceptions

class SenhaCurtaException(
    message: String ="error.senha.curta",
) : RuntimeException(message) {
}