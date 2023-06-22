package br.com.blas.forum.user.entrypoint.rest.dto.response

import br.com.blas.forum.user.entity.User

data class UserRegisteredResponse(
    val id: Int?
) {
    companion object {
        fun fromDomain(user: User): UserRegisteredResponse {
            return UserRegisteredResponse(id = user.id)
        }
    }
}