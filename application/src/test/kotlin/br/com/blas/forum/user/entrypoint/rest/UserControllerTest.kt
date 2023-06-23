package br.com.blas.forum.user.entrypoint.rest

import br.com.blas.forum.config.BaseTest
import br.com.blas.forum.helpers.TestsSqlScripts.Companion.CLEAR_ALL
import br.com.blas.forum.user.database.repository.UserModelRepository
import br.com.blas.forum.user.entrypoint.rest.dto.request.RegisterUserRequest
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.post
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
internal class UserControllerTest : BaseTest() {

    @Autowired
    lateinit var userModelRepository: UserModelRepository

    @Test
    @Sql(CLEAR_ALL)
    fun `Should receive a user and saved in database with success`() {
        val registerUserRequest = RegisterUserRequest("User 1", "user@gmail.com")
        val registerUserRequestInJson = mapper.writeValueAsString(registerUserRequest)

        wireMockServer.stubFor(
            post(UserController.URI)
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(registerUserRequestInJson)
                )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post(UserController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerUserRequestInJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val users = userModelRepository.findAll()
        val userRegistered =
            users.firstOrNull { it.id != null && it.name == registerUserRequest.name && it.email == registerUserRequest.email }

        assertNotNull(userRegistered)
    }

    @Test
    @Sql(CLEAR_ALL)
    fun `Should receive a user with an invalid name and not save to the database`() {
        val registerUserRequest = RegisterUserRequest("", "user@gmail.com")
        val registerUserRequestInJson = mapper.writeValueAsString(registerUserRequest)

        wireMockServer.stubFor(
            post(UserController.URI)
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(registerUserRequestInJson)
                )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post(UserController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerUserRequestInJson)
        )
            .andExpect(status().is4xxClientError)
            .andReturn()

        val users = userModelRepository.findAll()
        val userRegistered =
            users.firstOrNull { it.id != null && it.name == registerUserRequest.name && it.email == registerUserRequest.email }

        assertNull(userRegistered)
    }

    @Test
    @Sql(CLEAR_ALL)
    fun `Should receive a user with an invalid email and not save to the database`() {
        val registerUserRequest = RegisterUserRequest("User 1", "gmail.com")
        val registerUserRequestInJson = mapper.writeValueAsString(registerUserRequest)

        wireMockServer.stubFor(
            post(UserController.URI)
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(registerUserRequestInJson)
                )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post(UserController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerUserRequestInJson)
        )
            .andExpect(status().is4xxClientError)
            .andReturn()

        val users = userModelRepository.findAll()
        val userRegistered =
            users.firstOrNull { it.id != null && it.name == registerUserRequest.name && it.email == registerUserRequest.email }

        assertNull(userRegistered)
    }
}