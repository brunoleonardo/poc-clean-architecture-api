package br.com.blas.forum.page

data class Page<T>(
    val totalPages: Int,
    val totalElements: Long,
    val number: Int,
    val isLast: Boolean,
    val content: List<T>
)
