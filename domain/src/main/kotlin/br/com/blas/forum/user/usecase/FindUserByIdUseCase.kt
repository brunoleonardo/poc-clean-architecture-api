package br.com.blas.forum.user.usecase

import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.FindUserByIdGateway
import javax.inject.Named

@Named
class FindUserByIdUseCase(private val findUserByIdGateway: FindUserByIdGateway) {

    fun execute(userId: Int): Result<User> {
        return findUserByIdGateway.execute(userId)
    }


}