package br.com.blas.forum.user.provider

import br.com.blas.forum.exception.BusinessException
import br.com.blas.forum.user.database.model.UserModel
import br.com.blas.forum.user.database.repository.UserModelRepository
import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.SaveUserGateway
import org.springframework.stereotype.Component

@Component
class SaveUserProvider(private val userModelRepository: UserModelRepository) : SaveUserGateway {

    override fun execute(user: User): Result<User> {
        if (userModelRepository.existsByEmail(user.email)) {
            throw BusinessException("There is already a user with the given email")
        }

        val userModel = UserModel.fromDomain(user)

        return Result.success(userModelRepository.save(userModel).toDomain())
    }

}