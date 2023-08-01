package br.com.blas.forum.question.entrypoint.rest

import br.com.blas.forum.question.entrypoint.rest.dto.request.RegisterQuestionRequest
import br.com.blas.forum.question.entrypoint.rest.dto.response.QuestionByIdResponse
import br.com.blas.forum.question.entrypoint.rest.dto.response.QuestionRegisteredResponse
import br.com.blas.forum.question.usecase.FindQuestionByIdUseCase
import br.com.blas.forum.question.usecase.RegisterQuestionUseCase
import br.com.blas.forum.user.entrypoint.rest.dto.request.RegisterUserRequest.Companion.toDomain
import javax.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping(QuestionController.URI)
class QuestionController(
    private val findQuestionByIdUseCase: FindQuestionByIdUseCase,
    private val registerQuestionUseCase: RegisterQuestionUseCase,
) {
    companion object {
        const val URI = "/v1/questions"
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<QuestionByIdResponse> {
        return ResponseEntity.ok(QuestionByIdResponse.fromDomain(findQuestionByIdUseCase.execute(id).getOrThrow()))
    }

    @PostMapping
    fun register(
        @RequestBody @Valid request: RegisterQuestionRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<QuestionRegisteredResponse> {
        val questionCreated = registerQuestionUseCase.execute(request.toDomain()).getOrThrow()
        val uri = uriBuilder.path("$URI/${questionCreated.id}").build().toUri()

        return ResponseEntity.created(uri).body(QuestionRegisteredResponse.fromDomain(questionCreated))
    }
}