package br.com.blas.forum.user.usecase

import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.RegisterUserGateway
import javax.inject.Named

@Named
class RegisterUserUseCase(private val registerUserGateway: RegisterUserGateway) {
    fun execute(user: User): Result<User> {
        return registerUserGateway.execute(user)
    }
}