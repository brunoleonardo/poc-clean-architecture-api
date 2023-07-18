package br.com.blas.forum.user.gateway

import br.com.blas.forum.user.entity.User

interface RegisterUserGateway {
    fun execute(user: User): Result<User>
}