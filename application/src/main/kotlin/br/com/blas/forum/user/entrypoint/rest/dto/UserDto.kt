package br.com.blas.forum.user.entrypoint.rest.dto

import br.com.blas.forum.user.entity.User

data class UserDto(
    val id: Int?,
    val name: String,
    val email: String
) {
    companion object {
        fun fromDomain(user: User): UserDto {
            return UserDto(id = user.id, name = user.name, email = user.email)
        }
    }
}