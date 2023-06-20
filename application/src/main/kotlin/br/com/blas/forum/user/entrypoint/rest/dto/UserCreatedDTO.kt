package br.com.blas.forum.user.entrypoint.rest.dto

import br.com.blas.forum.user.model.User

data class UserCreatedDTO(
    val id: Int?
) {
    companion object {
        fun fromDomain(user: User): UserCreatedDTO {
            return UserCreatedDTO(id = user.id)
        }
    }
}