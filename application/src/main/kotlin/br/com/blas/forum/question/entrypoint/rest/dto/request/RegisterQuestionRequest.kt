package br.com.blas.forum.question.entrypoint.rest.dto.request

import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.user.entity.User
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class RegisterQuestionRequest(
    @field:NotEmpty(message = "Title cannot be empty")
    @field:Size(min = 2, max = 80, message = "Title must be between 2 and 80 characters")
    val title: String,

    @field:NotEmpty(message = "Description cannot be empty")
    @field:Size(min = 10, message = "Description must be at least 2 characters")
    val description: String,

    @field:NotNull(message = "User ID cannot be null")
    @field:Positive(message = "User ID must be a positive number greater than zero")
    val userId: Int,
) {
    fun toDomain(): Question {
        return Question(id = null, title, description, User(userId))
    }
}
