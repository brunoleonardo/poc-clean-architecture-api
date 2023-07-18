package br.com.blas.forum.user.provider

import br.com.blas.forum.exception.BusinessException
import br.com.blas.forum.user.database.model.UserModel
import br.com.blas.forum.user.database.repository.UserModelRepository
import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.RegisterUserGateway
import org.springframework.stereotype.Component

@Component
class RegisterUserProvider(private val userModelRepository: UserModelRepository) : RegisterUserGateway {
    companion object {
        const val USER_WITH_EXISTING_EMAIL = "There is already a user with the given email"
    }

    override fun execute(user: User): Result<User> {
        if (userModelRepository.existsByEmail(user.email)) {
            throw BusinessException(USER_WITH_EXISTING_EMAIL)
        }

        val userModel = UserModel.fromDomain(user)

        return Result.success(userModelRepository.save(userModel).toDomain())
    }
}