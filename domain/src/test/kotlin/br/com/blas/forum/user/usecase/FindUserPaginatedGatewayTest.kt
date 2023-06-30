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
        val users = generateUsersByQuantity(4)

        val page = Page(
            page = pageNumber,
            totalPages = 1,
            totalElements = users.size.toLong(),
            isLast = true,
            content = users
        )

        // When
        coEvery { findUserPaginatedGateway.execute(any(), any()) } returns page

        val result = FindUserPaginatedUseCase(findUserPaginatedGateway).execute(pageSize, pageNumber)

        // Then
        Assertions.assertTrue(result.page == pageNumber)
        Assertions.assertTrue(result.totalPages == 1)
        Assertions.assertTrue(result.totalElements == 4L)
        Assertions.assertTrue(result.isLast)
    }

    @Test
    fun `Should fetch a large list of users and then turn it into multiple pages`() {
        // Given
        val pageSize = 5
        val pageNumber = 0
        val users = generateUsersByQuantity(50)

        val page = Page(
            page = pageNumber,
            totalPages = 10,
            totalElements = users.size.toLong(),
            isLast = false,
            content = users
        )

        // When
        coEvery { findUserPaginatedGateway.execute(any(), any()) } returns page

        val result = FindUserPaginatedUseCase(findUserPaginatedGateway).execute(pageSize, pageNumber)

        // Then
        Assertions.assertTrue(result.page == pageNumber)
        Assertions.assertTrue(result.totalPages == (users.size / pageSize))
        Assertions.assertTrue(result.totalElements == users.size.toLong())
        Assertions.assertFalse(result.isLast)
    }

    private fun generateUsersByQuantity(quantity: Int): List<User> {
        val list = mutableListOf<User>()
        for (i in 1..quantity) {
            list.add(User(i, "User $i", "user$i@gmail.com"))
        }

        return list
    }

}