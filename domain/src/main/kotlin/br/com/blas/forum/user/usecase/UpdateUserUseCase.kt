package br.com.blas.forum.user.usecase

import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.UpdateUserGateway
import javax.inject.Named

@Named
class UpdateUserUseCase(private val updateUserGateway: UpdateUserGateway) {
    fun execute(user: User): Result<User> {
        return updateUserGateway.execute(user)
    }
}