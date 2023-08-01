package br.com.blas.forum.question.entrypoint.rest.dto.response

import br.com.blas.forum.question.entity.Question

data class QuestionRegisteredResponse(
    val id: Int,
) {
    companion object {
        fun fromDomain(question: Question): QuestionRegisteredResponse {
            return QuestionRegisteredResponse(question.id!!)
        }
    }
}
