package br.com.blas.forum.user.entrypoint.rest.dto.response

import br.com.blas.forum.user.entity.User

data class UserResponse(
    val id: Int?,
    val name: String,
    val email: String
) {
    companion object {
        fun fromDomain(user: User) = UserResponse(id = user.id, name = user.name, email = user.email)

    }
}