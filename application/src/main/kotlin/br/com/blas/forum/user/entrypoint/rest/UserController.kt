package br.com.blas.forum.user.entrypoint.rest

import br.com.blas.forum.user.entrypoint.rest.dto.SaveUserDto
import br.com.blas.forum.user.entrypoint.rest.dto.SaveUserDto.Companion.toDomain
import br.com.blas.forum.user.entrypoint.rest.dto.UserCreatedDto
import br.com.blas.forum.user.entrypoint.rest.dto.UserDto
import br.com.blas.forum.user.usecase.FindUserByIdUseCase
import br.com.blas.forum.user.usecase.SaveUserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(UserController.URI)
class UserController(
    private val saveUserUseCase: SaveUserUseCase,
    private val findUserByIdUseCase: FindUserByIdUseCase
) {

    companion object {
        const val URI = "/v1/users"
    }

    @PostMapping
    fun save(
        @RequestBody @Valid saveUserDTO: SaveUserDto,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<UserCreatedDto> {
        val userCreated = saveUserUseCase.execute(saveUserDTO.toDomain()).getOrThrow()
        val uri = uriBuilder.path("$URI/${userCreated.id}").build().toUri()

        return ResponseEntity.created(uri).body(UserCreatedDto.fromDomain(userCreated))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<UserDto> {
        return ResponseEntity.ok(UserDto.fromDomain(findUserByIdUseCase.execute(id).getOrThrow()))
    }

}