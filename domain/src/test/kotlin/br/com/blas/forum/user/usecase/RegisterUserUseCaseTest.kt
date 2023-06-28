package br.com.blas.forum.user.usecase

import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.RegisterUserGateway
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RegisterUserUseCaseTest {

    @BeforeEach
    fun initMocks() {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var registerUserGateway: RegisterUserGateway

    @Test
    fun `Should receive a user and saved in database with success`() {
        // Given
        val user = User(id = 1, name = "User 1", email = "user@gmail.com")

        // When
        coEvery { registerUserGateway.execute(any()) } returns Result.success(user)

        val result = RegisterUserUseCase(registerUserGateway).execute(user)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Should receive a user with an invalid name and not save to the database`() {
        // Given
        val userInvalid = User(id = null, name = "", email = "user@gmail.com")

        // When
        coEvery { registerUserGateway.execute(any()) } returns Result.failure(Exception())

        val result = RegisterUserUseCase(registerUserGateway).execute(userInvalid)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `Should receive a user with an invalid email and not save to the database`() {
        // Given
        val userInvalid = User(id = 1, name = "User 1", email = "invalid email")

        // When
        coEvery { registerUserGateway.execute(any()) } returns Result.failure(Exception())

        val result = RegisterUserUseCase(registerUserGateway).execute(userInvalid)

        // Then
        assertTrue(result.isFailure)
    }

}