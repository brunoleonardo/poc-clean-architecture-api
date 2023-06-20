package br.com.blas.forum.exception

class BusinessException(code: Int? = null, message: String? = "") : RuntimeException(message)
