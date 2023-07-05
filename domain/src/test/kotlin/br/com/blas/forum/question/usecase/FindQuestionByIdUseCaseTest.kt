package br.com.blas.forum.question.usecase

import br.com.blas.forum.exception.NotFoundException
import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.question.gateway.FindQuestionByIdGateway
import br.com.blas.forum.user.entity.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class FindQuestionByIdUseCaseTest {
    @BeforeEach
    fun initMocks() {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var findQuestionByIdGateway: FindQuestionByIdGateway

    @Test
    fun `Should fetch a question by id in the database with success`() {
        // Given
        val questionId = 1
        val user = User(id = 1, name = "User 1", email = "user@gmail.com")
        val question = Question(id = questionId, title = "Question 1", description = "Description question 1", user)

        // When
        coEvery { findQuestionByIdGateway.execute(any()) } returns Result.success(question)

        val result = FindQuestionByIdUseCase(findQuestionByIdGateway).execute(questionId)

        // Then
        Assertions.assertTrue(result.isSuccess)
    }

    @Test
    fun `Should fetch a question by id that does not exist in the database`() {
        // Given
        val questionId = (0..1000).random()

        // When
        coEvery { findQuestionByIdGateway.execute(any()) } returns Result.failure(NotFoundException(""))

        val result = FindQuestionByIdUseCase(findQuestionByIdGateway).execute(questionId)

        // Then
        Assertions.assertTrue(result.isFailure)
    }

}