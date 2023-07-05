package br.com.blas.forum.question.entrypoint.rest

import br.com.blas.forum.config.BaseTest
import br.com.blas.forum.helpers.TestsSqlScripts
import br.com.blas.forum.question.database.repository.QuestionModelRepository
import br.com.blas.forum.question.entrypoint.rest.dto.response.QuestionByIdResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals

@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
internal class QuestionControllerTest : BaseTest() {

    @Autowired
    lateinit var questionRepository: QuestionModelRepository

    @Test
    @Sql(TestsSqlScripts.CLEAR_ALL_TABLES, TestsSqlScripts.USERS, TestsSqlScripts.QUESTIONS)
    fun `Should bring existing question by id`() {
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
}