package br.com.blas.forum.user.database.model

import br.com.blas.forum.user.entity.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "user")
data class UserModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val name: String,
    val email: String,
) {
    fun toDomain() = User(
        id = id,
        name = name,
        email = email,
    )

    companion object {
        fun fromDomain(user: User) = UserModel(
            id = user.id,
            name = user.name,
            email = user.email,
        )
    }
}
