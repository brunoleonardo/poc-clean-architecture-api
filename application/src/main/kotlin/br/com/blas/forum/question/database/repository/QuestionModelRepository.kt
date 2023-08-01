package br.com.blas.forum.question.database.repository

import br.com.blas.forum.question.database.model.QuestionModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionModelRepository : JpaRepository<QuestionModel, Int>


