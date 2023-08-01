package br.com.blas.forum.question.database.model

import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.user.database.model.UserModel
import br.com.blas.forum.user.entity.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

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

    companion object {
        fun fromDomain(question: Question) = QuestionModel(
            id = question.id,
            title = question.title,
            description = question.description,
            user = UserModel(
                question.user.id,
            ),
        )
    }
}
