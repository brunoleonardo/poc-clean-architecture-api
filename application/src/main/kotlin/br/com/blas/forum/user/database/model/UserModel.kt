package br.com.blas.forum.user.database.model

import br.com.blas.forum.user.entity.User
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity(name = "user")
data class UserModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val name: String,
    val email: String
) {
    companion object {
        fun fromDomain(user: User) = UserModel(
            id = user.id,
            name = user.name,
            email = user.email
        )
    }

    fun toDomain() = User(
        id = id,
        name = name,
        email = email
    )
}
