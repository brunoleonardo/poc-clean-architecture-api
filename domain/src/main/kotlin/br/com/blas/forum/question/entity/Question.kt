package br.com.blas.forum.question.entity

import br.com.blas.forum.user.entity.User

data class Question(
    val id: Int?,
    val title: String,
    val description: String,
    val user: User,
)
