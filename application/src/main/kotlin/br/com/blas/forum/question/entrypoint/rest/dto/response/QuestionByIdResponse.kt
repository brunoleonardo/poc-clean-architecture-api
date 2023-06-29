package br.com.blas.forum.question.entrypoint.rest.dto.response

import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.user.entrypoint.rest.dto.response.UserResponse

data class QuestionByIdResponse(
    val id: Int?,
    val title: String,
    val description: String,
    val user: UserResponse,
    // TODO: ANSWERS
) {
    companion object {
        fun fromDomain(question: Question): QuestionByIdResponse {
            val user = question.user

            return QuestionByIdResponse(
                question.id,
                question.title,
                question.description,
                UserResponse(
                    user.id,
                    user.name,
                    user.email
                )
            )
        }
    }

}
