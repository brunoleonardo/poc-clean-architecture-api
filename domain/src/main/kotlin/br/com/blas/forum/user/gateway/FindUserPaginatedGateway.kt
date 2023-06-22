package br.com.blas.forum.user.gateway

import br.com.blas.forum.page.Page
import br.com.blas.forum.user.entity.User

interface FindUserPaginatedGateway {

    fun execute(pageSize: Int, pageNumber: Int): Page<User>

}