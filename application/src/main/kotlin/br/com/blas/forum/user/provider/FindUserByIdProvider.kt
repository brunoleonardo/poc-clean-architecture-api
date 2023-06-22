package br.com.blas.forum.user.provider

import br.com.blas.forum.exception.NotFoundException
import br.com.blas.forum.user.database.repository.UserModelRepository
import br.com.blas.forum.user.entity.User
import br.com.blas.forum.user.gateway.FindUserByIdGateway
import org.springframework.stereotype.Component

@Component
class FindUserByIdProvider(private val userModelRepository: UserModelRepository) : FindUserByIdGateway {

    override fun execute(userId: Int): Result<User> {
        val user = userModelRepository.findById(userId).orElseThrow { NotFoundException("User not found") }

        return Result.success(user.toDomain())
    }

}