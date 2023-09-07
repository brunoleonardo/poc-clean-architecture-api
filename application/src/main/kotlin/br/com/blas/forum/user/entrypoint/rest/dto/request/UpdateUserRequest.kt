package br.com.blas.forum.user.entrypoint.rest.dto.request

import br.com.blas.forum.user.entity.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UpdateUserRequest(
    @field:NotNull
    val id: Int,

    @field:NotEmpty(message = "Name cannot be empty")
    @field:Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    val name: String,

    @field:NotEmpty(message = "E-mail cannot be empty")
    @field:Size(min = 5, max = 100, message = "E-mail must be between 5 and 100 characters")
    @field:Email(message = "Invalid e-mail")
    val email: String,
) {
    companion object {
        fun UpdateUserRequest.toDomain(): User {
            return User(id, name, email)
        }
    }
}
