package br.com.blas.forum.question.gateway

import br.com.blas.forum.question.entity.Question

interface RegisterQuestionGateway {
    fun execute(question: Question): Result<Question>
}