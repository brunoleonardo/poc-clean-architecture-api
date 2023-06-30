package br.com.blas.forum.user.usecase

import br.com.blas.forum.data.model.Page
import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.FindUserPaginatedGateway
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FindUserPaginatedGatewayTest {

    @BeforeEach
    fun initMocks() {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var findUserPaginatedGateway: FindUserPaginatedGateway

    @Test
    fun `Should fetch a list of user and then turn it into a page`() {
        // Given
        val pageSize = 5
        val pageNumber = 0
        val users = listOf(
            User(1, "User 1", "user1@gmail.com"),
            User(2, "User 2", "user2@gmail.com"),
            User(3, "User 3", "user3@gmail.com"),
            User(4, "User 4", "user4@gmail.com"),
            User(5, "User 5", "user5@gmail.com")
        )

        val page = Page(
            page = pageNumber,
            totalPages = 1,
            totalElements = pageSize.toLong(),
            isLast = true,
            content = users
        )

        // When
        coEvery { findUserPaginatedGateway.execute(any(), any()) } returns page

        val result = FindUserPaginatedUseCase(findUserPaginatedGateway).execute(pageSize, pageNumber)

        // Then
        Assertions.assertTrue(result.page == pageNumber)
        Assertions.assertTrue(result.totalPages == 1)
        Assertions.assertTrue(result.totalElements == users.size.toLong())
        Assertions.assertTrue(result.isLast)
    }

}