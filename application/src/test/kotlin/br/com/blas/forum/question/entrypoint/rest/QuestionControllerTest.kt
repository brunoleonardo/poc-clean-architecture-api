package br.com.blas.forum.question.entrypoint.rest

import br.com.blas.forum.config.BaseTest
import br.com.blas.forum.helpers.TestsSqlScripts.Companion.CLEAR_ALL_TABLES
import br.com.blas.forum.helpers.TestsSqlScripts.Companion.QUESTIONS
import br.com.blas.forum.helpers.TestsSqlScripts.Companion.USERS
import br.com.blas.forum.question.database.repository.QuestionModelRepository
import br.com.blas.forum.question.entrypoint.rest.dto.request.RegisterQuestionRequest
import br.com.blas.forum.question.entrypoint.rest.dto.response.QuestionByIdResponse
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
internal class QuestionControllerTest : BaseTest() {

    @Autowired
    lateinit var questionRepository: QuestionModelRepository

    @Test
    @Sql(CLEAR_ALL_TABLES, USERS, QUESTIONS)
    fun `should bring existing question by id`() {
        val user = QuestionByIdResponse.UserResponse(1, "Alberto Silva", "beto@gmail.com")
        val question = QuestionByIdResponse(1, "Question 1", "Description of Question 1", user)
        val expectedResponse = mapper.writeValueAsString(question)

        val mockMvcResponse = mockMvc.perform(
            MockMvcRequestBuilders.get(QuestionController.URI + "/${question.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val response = mockMvcResponse.response.contentAsString

        assertEquals(response, expectedResponse)
    }

    @Test
    @Sql(CLEAR_ALL_TABLES, USERS, QUESTIONS)
    fun `should receive a question and saved in database with success`() {
        val registerQuestionRequest =
            RegisterQuestionRequest("Question XYZ", "Description of Question XYX", 1)
        val registerQuestionRequestInJson = mapper.writeValueAsString(registerQuestionRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post(QuestionController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerQuestionRequestInJson)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        val questions = questionRepository.findAll()
        val questionRegistered =
            questions.firstOrNull {
                it.id != null
                        && it.title == registerQuestionRequest.title
                        && it.description == registerQuestionRequest.description
            }

        assertNotNull(questionRegistered)
    }
}