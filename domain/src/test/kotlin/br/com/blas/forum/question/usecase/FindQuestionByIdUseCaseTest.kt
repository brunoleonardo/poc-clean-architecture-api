package br.com.blas.forum.question.usecase

import br.com.blas.forum.exception.NotFoundException
import br.com.blas.forum.helpers.model.QuestionTest
import br.com.blas.forum.question.gateway.FindQuestionByIdGateway
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
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
        val question = QuestionTest.build(id = 1)

        every { findQuestionByIdGateway.execute(any()) } returns Result.success(question)

        val result = FindQuestionByIdUseCase(findQuestionByIdGateway).execute(question.id!!)

        // Then
        assertTrue(result.isSuccess)
        assertThat(result.getOrNull()?.id).isEqualTo(question.id!!)
        assertThat(result.getOrNull()?.user?.id).isEqualTo(question.user.id)
    }

    @Test
    fun `Should fetch a question by id that does not exist in the database`() {
        val questionId = (0..1000).random()

        every { findQuestionByIdGateway.execute(any()) } returns Result.failure(NotFoundException(""))

        val result = FindQuestionByIdUseCase(findQuestionByIdGateway).execute(questionId)

        assertTrue(result.isFailure)
    }

}