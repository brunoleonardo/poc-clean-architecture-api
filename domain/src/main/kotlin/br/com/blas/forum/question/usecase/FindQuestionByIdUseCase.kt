package br.com.blas.forum.question.usecase

import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.question.gateway.FindQuestionByIdGateway
import javax.inject.Named

@Named
class FindQuestionByIdUseCase(private val findQuestionByIdGateway: FindQuestionByIdGateway) {

    fun execute(id: Int): Result<Question> {
        return findQuestionByIdGateway.execute(id)
    }


}