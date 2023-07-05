package br.com.blas.forum.question.provider

import br.com.blas.forum.exception.NotFoundException
import br.com.blas.forum.question.database.repository.QuestionModelRepository
import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.question.gateway.FindQuestionByIdGateway
import org.springframework.stereotype.Component

@Component
class FindQuestionByIdProvider(private val questionRepository: QuestionModelRepository) : FindQuestionByIdGateway {
    companion object {
        const val QUESTION_NOT_FOUND = "Question not found"
    }

    override fun execute(id: Int): Result<Question> {
        val question =
            questionRepository.findByIdAndFetchUserEagerly(id).orElseThrow { NotFoundException(QUESTION_NOT_FOUND) }

        return Result.success(question.toDomain())
    }
}