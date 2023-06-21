package br.com.blas.forum.user.entrypoint.rest.dto

import br.com.blas.forum.user.entity.User

data class UserCreatedDto(
    val id: Int?
) {
    companion object {
        fun fromDomain(user: User): UserCreatedDto {
            return UserCreatedDto(id = user.id)
        }
    }
}