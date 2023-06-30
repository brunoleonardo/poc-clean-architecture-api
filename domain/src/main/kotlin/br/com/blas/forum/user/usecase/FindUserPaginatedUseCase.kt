package br.com.blas.forum.user.usecase

import br.com.blas.forum.data.model.Page
import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.FindUserPaginatedGateway
import javax.inject.Named

@Named
class FindUserPaginatedUseCase(private val findUserPaginatedGateway: FindUserPaginatedGateway) {

    fun execute(pageSize: Int, pageNumber: Int): Page<User> {
        return findUserPaginatedGateway.execute(pageSize, pageNumber)
    }

}