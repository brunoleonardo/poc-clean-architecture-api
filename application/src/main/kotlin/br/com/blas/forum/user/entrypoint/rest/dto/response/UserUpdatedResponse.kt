package br.com.blas.forum.user.entrypoint.rest.dto.response

import br.com.blas.forum.user.entity.User

data class UserUpdatedResponse(
    val id: Int,
    val name: String,
    val email: String
) {
    companion object {
        fun fromDomain(user: User): UserUpdatedResponse {
            return UserUpdatedResponse(user.id!!, user.name, user.email)
        }
    }
}
