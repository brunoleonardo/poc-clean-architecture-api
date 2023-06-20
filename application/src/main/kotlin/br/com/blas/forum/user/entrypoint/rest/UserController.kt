package br.com.blas.forum.user.entrypoint.rest

import br.com.blas.forum.user.entrypoint.rest.dto.SaveUserDTO
import br.com.blas.forum.user.entrypoint.rest.dto.SaveUserDTO.Companion.toDomain
import br.com.blas.forum.user.entrypoint.rest.dto.UserCreatedDTO
import br.com.blas.forum.user.usecase.SaveUserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/v1/users")
class UserController(private val saveUserUseCase: SaveUserUseCase) {
    @PostMapping
    fun save(
        @RequestBody @Valid saveUserDTO: SaveUserDTO,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<UserCreatedDTO> {
        val userCreated = saveUserUseCase.execute(saveUserDTO.toDomain()).getOrThrow()
        val uri = uriBuilder.path("/v1/users/${userCreated.id}").build().toUri()

        return ResponseEntity.created(uri).body(UserCreatedDTO.fromDomain(userCreated))
    }
}