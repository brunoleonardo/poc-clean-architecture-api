package br.com.blas.forum.question.database.model

import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.user.database.model.UserModel
import br.com.blas.forum.user.entity.User
import javax.persistence.*

@Entity
@Table(name = "question")
data class QuestionModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val title: String,
    @Column(columnDefinition = "MEDIUMTEXT")
    val description: String,
    @ManyToOne(fetch = FetchType.LAZY)
    val user: UserModel
) {
    fun toDomain() = Question(
        id = id,
        title = title,
        description = description,
        user = User(
            id = user.id,
            name = user.name,
            email = user.email
        )
    )

}
