package br.com.blas.forum.data.model

data class Page<T>(
    val page: Int,
    val totalPages: Int,
    val totalElements: Long,
    val isLast: Boolean,
    val content: List<T>
)
