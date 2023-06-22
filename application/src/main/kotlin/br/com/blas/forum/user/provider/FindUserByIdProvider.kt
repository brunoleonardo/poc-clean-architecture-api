package br.com.blas.forum.user.provider

import br.com.blas.forum.exception.NotFoundException
import br.com.blas.forum.user.database.repository.UserModelRepository
import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.FindUserByIdGateway
import org.springframework.stereotype.Component

@Component
class FindUserByIdProvider(private val userModelRepository: UserModelRepository) : FindUserByIdGateway {

    companion object {
        const val USER_NOT_FOUND = "User not found"
    }

    override fun execute(userId: Int): Result<User> {
        val user = userModelRepository.findById(userId).orElseThrow { NotFoundException(USER_NOT_FOUND) }

        return Result.success(user.toDomain())
    }

}