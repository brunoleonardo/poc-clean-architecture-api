package br.com.blas.forum.user.gateway

import br.com.blas.forum.user.entity.User

interface SaveUserGateway {

    fun execute(user: User): Result<User>

}