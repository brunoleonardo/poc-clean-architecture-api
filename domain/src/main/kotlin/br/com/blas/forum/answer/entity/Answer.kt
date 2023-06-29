package br.com.blas.forum.answer.entity

import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.user.entity.User

data class Answer(
    val id: Int?,
    val description: String,
    val question: Question,
    val user: User,
)
