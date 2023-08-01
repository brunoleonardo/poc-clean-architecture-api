package br.com.blas.forum.question.usecase

import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.question.gateway.RegisterQuestionGateway
import javax.inject.Named

@Named
class RegisterQuestionUseCase(private val registerQuestionGateway: RegisterQuestionGateway) {
    fun execute(question: Question): Result<Question> {
        return registerQuestionGateway.execute(question)
    }
}