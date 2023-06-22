package br.com.blas.forum.user.entrypoint.rest

import br.com.blas.forum.user.entrypoint.rest.dto.RegisterUserDto
import br.com.blas.forum.user.entrypoint.rest.dto.RegisterUserDto.Companion.toDomain
import br.com.blas.forum.user.entrypoint.rest.dto.UserDto
import br.com.blas.forum.user.entrypoint.rest.dto.UserRegisteredDto
import br.com.blas.forum.user.usecase.FindUserByIdUseCase
import br.com.blas.forum.user.usecase.RegisterUserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(UserController.URI)
class UserController(
    private val registerUserUseCase: RegisterUserUseCase,
    private val findUserByIdUseCase: FindUserByIdUseCase
) {

    companion object {
        const val URI = "/v1/users"
    }

    @PostMapping
    fun register(
        @RequestBody @Valid registerUserDTO: RegisterUserDto,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<UserRegisteredDto> {
        val userCreated = registerUserUseCase.execute(registerUserDTO.toDomain()).getOrThrow()
        val uri = uriBuilder.path("$URI/${userCreated.id}").build().toUri()

        return ResponseEntity.created(uri).body(UserRegisteredDto.fromDomain(userCreated))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<UserDto> {
        return ResponseEntity.ok(UserDto.fromDomain(findUserByIdUseCase.execute(id).getOrThrow()))
    }

}