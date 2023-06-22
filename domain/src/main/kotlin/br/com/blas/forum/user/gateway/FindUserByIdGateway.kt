package br.com.blas.forum.user.gateway

import br.com.blas.forum.user.entity.User

interface FindUserByIdGateway {

    fun execute(userId: Int): Result<User>

}