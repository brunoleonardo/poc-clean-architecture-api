package br.com.blas.forum.question.gateway

import br.com.blas.forum.question.entity.Question

interface FindQuestionByIdGateway {
    fun execute(id: Int): Result<Question>
}