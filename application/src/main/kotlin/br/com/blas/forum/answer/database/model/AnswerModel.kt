package br.com.blas.forum.answer.database.model

import br.com.blas.forum.question.database.model.QuestionModel
import br.com.blas.forum.user.database.model.UserModel
import javax.persistence.*

@Entity
@Table(name = "answer")
data class AnswerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(columnDefinition = "MEDIUMTEXT")
    val description: String,
    @ManyToOne(fetch = FetchType.LAZY)
    val question: QuestionModel,
    @ManyToOne(fetch = FetchType.LAZY)
    val user: UserModel
)
