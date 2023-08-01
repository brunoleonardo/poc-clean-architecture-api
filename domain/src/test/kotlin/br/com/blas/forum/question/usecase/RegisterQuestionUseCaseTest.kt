package br.com.blas.forum.question.usecase

import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.question.gateway.RegisterQuestionGateway
import br.com.blas.forum.user.entity.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RegisterQuestionUseCaseTest {

    @BeforeEach
    fun initMocks() {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var registerQuestionGateway: RegisterQuestionGateway

    @Test
    fun `Should receive a question and saved in database with success`() {
        // Given
        val question =
            Question(id = 1, title = "Question XYZ", description = "Description of Question XYZ", User(1))

        // When
        coEvery { registerQuestionGateway.execute(any()) } returns Result.success(question)

        val result = RegisterQuestionUseCase(registerQuestionGateway).execute(question)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Should receive a question with an invalid title and not save to the database`() {
        // Given
        val questionInvalid = Question(id = 1, title = "", description = "Description of Question Invalid", User(1))

        // When
        coEvery { registerQuestionGateway.execute(any()) } returns Result.failure(Exception())

        val result = RegisterQuestionUseCase(registerQuestionGateway).execute(questionInvalid)

        // Then
        assertTrue(result.isFailure)
    }
}