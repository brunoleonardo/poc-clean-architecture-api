package br.com.blas.forum.user.usecase

import br.com.blas.forum.exception.BusinessException
import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.UpdateUserGateway
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UpdateUserUseCaseTest {

    @BeforeEach
    fun initMocks() {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var updateUserGateway: UpdateUserGateway

    @Test
    fun `Should receive a user and update in database with success`() {
        // Given
        val user = User(id = 1, name = "User 1", email = "user@gmail.com")

        // When
        coEvery { updateUserGateway.execute(any()) } returns Result.success(user)

        val result = UpdateUserUseCase(updateUserGateway).execute(user)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Should receive a user with an email existent and not save to the database`() {
        // Given
        val userInvalid = User(id = 1, name = "User 1", email = "user@gmail")

        // When
        coEvery { updateUserGateway.execute(any()) } returns Result.failure(BusinessException("There is already a user with the given email"))

        val result = UpdateUserUseCase(updateUserGateway).execute(userInvalid)

        // Then
        assertTrue(result.isFailure)
    }

}