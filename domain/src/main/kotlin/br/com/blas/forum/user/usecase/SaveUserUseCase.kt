package br.com.blas.forum.user.usecase

import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.SaveUserGateway
import javax.inject.Named

@Named
class SaveUserUseCase(private val saveUserGateway: SaveUserGateway) {

    fun execute(user: User): Result<User> {
        return saveUserGateway.execute(user)
    }

}