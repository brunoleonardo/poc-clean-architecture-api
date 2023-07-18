package br.com.blas.forum.answer.database.repository

import br.com.blas.forum.answer.database.model.AnswerModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswerModelRepository : JpaRepository<AnswerModel, Int>