package br.com.blas.forum.user.provider

import br.com.blas.forum.data.model.Page
import br.com.blas.forum.user.database.repository.UserModelRepository
import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.FindUserPaginatedGateway
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class FindUserPaginatedProvider(private val userModelRepository: UserModelRepository) : FindUserPaginatedGateway {
    override fun execute(pageSize: Int, pageNumber: Int): Page<User> {
        val paging = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending())
        val result = userModelRepository.findAll(paging)

        return Page(
            result.number,
            result.totalPages,
            result.totalElements,
            result.isLast,
            result.content.map { it.toDomain() })
    }
}