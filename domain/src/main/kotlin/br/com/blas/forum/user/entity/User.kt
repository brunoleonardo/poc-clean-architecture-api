package br.com.blas.forum.user.entity

data class User(
    val id: Int?,
    val name: String,
    val email: String,
) {
    constructor(id: Int?) : this(id, "", "")
}