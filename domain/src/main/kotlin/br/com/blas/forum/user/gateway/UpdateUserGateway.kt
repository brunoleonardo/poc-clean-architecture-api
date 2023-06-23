package br.com.blas.forum.user.gateway

import br.com.blas.forum.user.entity.User

interface UpdateUserGateway {

    fun execute(user: User): Result<User>

}