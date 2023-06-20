package br.com.blas.forum.user.gateway

import br.com.blas.forum.user.model.User

interface SaveUserGateway {
    fun execute(user: User): Result<User>
}