package br.com.blas.forum.user.database.repository

import br.com.blas.forum.user.database.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserModelRepository : JpaRepository<UserModel, Int> {

    fun existsByEmail(email: String): Boolean

    fun existsByEmailAndIdNot(email: String, id: Int): Boolean
}