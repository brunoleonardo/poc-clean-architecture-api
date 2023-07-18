package br.com.blas.forum.question.entrypoint.rest

import br.com.blas.forum.question.entrypoint.rest.dto.response.QuestionByIdResponse
import br.com.blas.forum.question.usecase.FindQuestionByIdUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(QuestionController.URI)
class QuestionController(
    private val findQuestionByIdUseCase: FindQuestionByIdUseCase
) {
    companion object {
        const val URI = "/v1/questions"
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<QuestionByIdResponse> {
        return ResponseEntity.ok(QuestionByIdResponse.fromDomain(findQuestionByIdUseCase.execute(id).getOrThrow()))
    }
}