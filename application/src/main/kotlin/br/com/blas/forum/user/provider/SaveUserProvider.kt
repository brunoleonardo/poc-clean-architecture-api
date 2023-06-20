package br.com.blas.forum.user.provider

import br.com.blas.forum.user.database.model.UserModel
import br.com.blas.forum.user.database.repository.UserModelRepository
import br.com.blas.forum.user.gateway.SaveUserGateway
import br.com.blas.forum.user.model.User
import org.springframework.stereotype.Component

@Component
class SaveUserProvider(private val userModelRepository: UserModelRepository) : SaveUserGateway {
    override fun execute(user: User): Result<User> {
        val userModel = UserModel.fromDomain(user)

        return Result.success(userModelRepository.save(userModel).toDomain())
    }
}