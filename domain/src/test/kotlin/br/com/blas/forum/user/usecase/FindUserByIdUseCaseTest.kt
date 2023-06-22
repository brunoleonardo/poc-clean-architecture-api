package br.com.blas.forum.user.usecase

import br.com.blas.forum.exception.NotFoundException
import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.FindUserByIdGateway
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FindUserByIdUseCaseTest {

    @BeforeEach
    fun initMocks() {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var findUserByIdGateway: FindUserByIdGateway

    @Test
    fun `Should fetch a user by id in the database with success`() {
        // Given
        val userId = 1
        val user = User(id = userId, name = "User 1", email = "user@gmail.com")

        // When
        coEvery { findUserByIdGateway.execute(any()) } returns Result.success(user)

        val result = FindUserByIdUseCase(findUserByIdGateway).execute(userId)

        // Then
        Assertions.assertTrue(result.isSuccess)
    }

    @Test
    fun `Should fetch a user by id that does not exist in the database`() {
        // Given
        val userId = 1

        // When
        coEvery { findUserByIdGateway.execute(any()) } returns Result.failure(NotFoundException(""))

        val result = FindUserByIdUseCase(findUserByIdGateway).execute(userId)

        // Then
        Assertions.assertTrue(result.isFailure)
    }


}