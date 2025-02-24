package com.ricky.adocao.exception

class SenhaCurtaException(
    message: String ="error.senha.curta",
) : RuntimeException(message) {
}