package br.com.blas.forum.helpers.model

import br.com.blas.forum.user.entity.User

object UserTest {
    fun build(
        id: Int? = 1,
        name: String = "User 1",
        email: String = "user@gmail.com"
    ) = User(
        id = id, name = name, email = email
    )
}