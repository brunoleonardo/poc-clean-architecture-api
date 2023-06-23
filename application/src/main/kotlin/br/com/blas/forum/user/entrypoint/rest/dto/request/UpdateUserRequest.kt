package br.com.blas.forum.user.entrypoint.rest.dto.request

import br.com.blas.forum.user.entity.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UpdateUserRequest(
    @field:NotNull
    val id: Long,

    @field:NotEmpty(message = "Name cannot be empty")
    @field:Size(min = 5, max = 100, message = "Name must be between 5 and 100 characteres")
    val name: String,

    @field:NotEmpty(message = "Name cannot be empty")
    @field:Size(min = 5, max = 100, message = "E-mail must be between 5 and 100 characteres")
    @field:Email(message = "Invalid email")
    val email: String
) {
    companion object {
        fun UpdateUserRequest.toDomain(): User {
            return User(id = null, name, email)
        }
    }
}
