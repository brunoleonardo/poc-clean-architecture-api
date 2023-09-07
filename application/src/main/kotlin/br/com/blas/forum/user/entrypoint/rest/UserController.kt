package br.com.blas.forum.user.entrypoint.rest

import br.com.blas.forum.data.model.Page
import br.com.blas.forum.user.entrypoint.rest.dto.request.RegisterUserRequest
import br.com.blas.forum.user.entrypoint.rest.dto.request.RegisterUserRequest.Companion.toDomain
import br.com.blas.forum.user.entrypoint.rest.dto.request.UpdateUserRequest
import br.com.blas.forum.user.entrypoint.rest.dto.request.UpdateUserRequest.Companion.toDomain
import br.com.blas.forum.user.entrypoint.rest.dto.response.UserRegisteredResponse
import br.com.blas.forum.user.entrypoint.rest.dto.response.UserResponse
import br.com.blas.forum.user.entrypoint.rest.dto.response.UserUpdatedResponse
import br.com.blas.forum.user.usecase.FindUserByIdUseCase
import br.com.blas.forum.user.usecase.FindUserPaginatedUseCase
import br.com.blas.forum.user.usecase.RegisterUserUseCase
import br.com.blas.forum.user.usecase.UpdateUserUseCase
import javax.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping(UserController.URI)
class UserController(
    private val findUserPaginatedUseCase: FindUserPaginatedUseCase,
    private val findUserByIdUseCase: FindUserByIdUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) {
    companion object {
        const val URI = "/v1/users"
    }

    @GetMapping
    fun list(@PageableDefault(size = 5, page = 0) pagination: Pageable): Page<UserResponse> {
        val result = findUserPaginatedUseCase.execute(pagination.pageSize, pagination.pageNumber)

        return Page(
            result.page,
            result.totalPages,
            result.totalElements,
            result.isLast,
            result.content.map { UserResponse.fromDomain(it) },
        )
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

    @PutMapping
    fun update(@RequestBody @Valid request: UpdateUserRequest): ResponseEntity<UserUpdatedResponse> {
        val userUpdated = updateUserUseCase.execute(request.toDomain()).getOrThrow()

        return ResponseEntity.ok(UserUpdatedResponse.fromDomain(userUpdated))
    }
}


