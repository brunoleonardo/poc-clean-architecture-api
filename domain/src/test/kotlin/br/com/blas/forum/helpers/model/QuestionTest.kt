package br.com.blas.forum.helpers.model

import br.com.blas.forum.question.entity.Question
import br.com.blas.forum.user.entity.User

object QuestionTest {
    fun build(
        id: Int? = 1,
        title: String = "Question 1",
        description: String = "Description question 1",
        user: User = UserTest.build()
    ) = Question(
        id = id, title = title, description = description, user = user
    )
}