package br.com.blas.forum.user.entrypoint.rest

import br.com.blas.forum.page.Page
import br.com.blas.forum.user.entrypoint.rest.dto.request.RegisterUserRequest
import br.com.blas.forum.user.entrypoint.rest.dto.request.RegisterUserRequest.Companion.toDomain
import br.com.blas.forum.user.entrypoint.rest.dto.response.UserRegisteredResponse
import br.com.blas.forum.user.entrypoint.rest.dto.response.UserResponse
import br.com.blas.forum.user.usecase.FindUserByIdUseCase
import br.com.blas.forum.user.usecase.FindUserPaginatedUseCase
import br.com.blas.forum.user.usecase.RegisterUserUseCase
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(UserController.URI)
class UserController(
    private val registerUserUseCase: RegisterUserUseCase,
    private val findUserByIdUseCase: FindUserByIdUseCase,
    private val findUserPaginatedUseCase: FindUserPaginatedUseCase,
) {

    companion object {
        const val URI = "/v1/users"
    }

    @GetMapping
    fun list(pagination: Pageable): Page<UserResponse> {
        val result = findUserPaginatedUseCase.execute(pagination.pageSize, pagination.pageNumber)

        return Page(
            result.totalPages,
            result.totalElements,
            result.number,
            result.isLast,
            result.content.map { UserResponse.fromDomain(it) })
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(UserResponse.fromDomain(findUserByIdUseCase.execute(id).getOrThrow()))
    }

    @PostMapping
    fun register(
        @RequestBody @Valid request: RegisterUserRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<UserRegisteredResponse> {
        val userCreated = registerUserUseCase.execute(request.toDomain()).getOrThrow()
        val uri = uriBuilder.path("$URI/${userCreated.id}").build().toUri()

        return ResponseEntity.created(uri).body(UserRegisteredResponse.fromDomain(userCreated))
    }

}


