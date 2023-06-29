package br.com.blas.forum.question.database.repository

import br.com.blas.forum.question.database.model.QuestionModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface QuestionModelRepository : JpaRepository<QuestionModel, Int> {

    @Query("SELECT q FROM QuestionModel q JOIN FETCH q.user WHERE q.id = (:id)")
    fun findByIdAndFetchUserEagerly(@Param("id") id: Int): Optional<QuestionModel>

}


