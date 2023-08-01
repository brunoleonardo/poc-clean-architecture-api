package br.com.blas.forum.question.provider

import br.com.blas.forum.exception.BusinessException
import br.com.blas.forum.question.database.model.QuestionModel
import br.com.blas.forum.question.database.repository.QuestionModelRepository
import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.question.gateway.RegisterQuestionGateway
import br.com.blas.forum.user.database.repository.UserModelRepository
import org.springframework.stereotype.Component

@Component
class RegisterQuestionProvider(
    private val questionModelRepository: QuestionModelRepository,
    private val userModelRepository: UserModelRepository
) : RegisterQuestionGateway {
    companion object {
        const val USER_NOT_FOUND = "User not found"
    }

    override fun execute(question: Question): Result<Question> {
        if (!userModelRepository.existsById(question.user.id!!)) {
            throw BusinessException(USER_NOT_FOUND)
        }

        val questionModel = QuestionModel.fromDomain(question)

        return Result.success(questionModelRepository.save(questionModel).toDomain())
    }
}