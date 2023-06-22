package br.com.blas.forum.user.entrypoint.rest.dto

import br.com.blas.forum.user.entity.User

data class UserRegisteredDto(
    val id: Int?
) {
    companion object {
        fun fromDomain(user: User): UserRegisteredDto {
            return UserRegisteredDto(id = user.id)
        }
    }
}