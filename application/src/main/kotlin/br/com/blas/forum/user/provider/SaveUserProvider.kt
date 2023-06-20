package br.com.blas.forum.user.provider

import br.com.blas.forum.exception.BusinessException
import br.com.blas.forum.user.database.model.UserModel
import br.com.blas.forum.user.database.repository.UserModelRepository
import br.com.blas.forum.user.gateway.SaveUserGateway
import br.com.blas.forum.user.model.User
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class SaveUserProvider(private val userModelRepository: UserModelRepository) : SaveUserGateway {

    override fun execute(user: User): Result<User> {
        validateExistsByEmail(user)

        val userModel = UserModel.fromDomain(user)

        return Result.success(userModelRepository.save(userModel).toDomain())
    }

    override fun validateExistsByEmail(user: User) {
        if (userModelRepository.findByEmail(user.email) != null) {
            throw BusinessException(
                HttpStatus.BAD_REQUEST.value(),
                "A user with this email already exists"
            )
        }
    }

}