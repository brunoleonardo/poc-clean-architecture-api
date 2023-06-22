package br.com.blas.forum.page

data class Page<T>(
    val page: Int,
    val totalPages: Int,
    val totalElements: Long,
    val isLast: Boolean,
    val content: List<T>
)
