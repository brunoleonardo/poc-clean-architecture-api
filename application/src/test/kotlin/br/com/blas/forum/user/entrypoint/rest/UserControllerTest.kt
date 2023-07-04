package br.com.blas.forum.user.entrypoint.rest

import br.com.blas.forum.config.BaseTest
import br.com.blas.forum.data.model.Page
import br.com.blas.forum.helpers.TestsSqlScripts.Companion.CLEAR_ALL_TABLES
import br.com.blas.forum.helpers.TestsSqlScripts.Companion.USERS
import br.com.blas.forum.user.database.repository.UserModelRepository
import br.com.blas.forum.user.entrypoint.rest.dto.request.RegisterUserRequest
import br.com.blas.forum.user.entrypoint.rest.dto.response.UserResponse
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.*

@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
internal class UserControllerTest : BaseTest() {

    @Autowired
    lateinit var userModelRepository: UserModelRepository

    @Test
    @Sql(CLEAR_ALL_TABLES, USERS)
    fun `Should return a paged list of users`() {
        val pageable = PageRequest.of(0, 10)

        val mockMvcResponse = mockMvc.perform(
            MockMvcRequestBuilders.get(UserController.URI + "?page=${pageable.pageNumber}&size=${pageable.pageSize}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val response: Page<UserResponse> = mapper.readValue(mockMvcResponse.response.contentAsString)

        assertTrue(response.page == 0)
        assertTrue(response.totalPages == 2)
        assertFalse(response.isLast)
        assertFalse(response.content.isEmpty())
    }

    @Test
    @Sql(CLEAR_ALL_TABLES, USERS)
    fun `Should bring existing user by id`() {
        val userResponse = UserResponse(1, "Alberto Silva", "beto@gmail.com")
        val expectedResponse = mapper.writeValueAsString(userResponse)

        wireMockServer.stubFor(
            get(UserController.URI + "/${userResponse.id}")
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                )
        )

        val mockMvcResponse = mockMvc.perform(
            MockMvcRequestBuilders.get(UserController.URI + "/${userResponse.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val response = mockMvcResponse.response.contentAsString

        assertEquals(response, expectedResponse)
    }

    @Test
    @Sql(CLEAR_ALL_TABLES, USERS)
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
            users.firstOrNull {
                it.id != null && it.name == registerUserRequest.name && it.email == registerUserRequest.email
            }

        assertNotNull(userRegistered)
    }

    @Test
    @Sql(CLEAR_ALL_TABLES, USERS)
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
            users.firstOrNull {
                it.id != null && it.name == registerUserRequest.name && it.email == registerUserRequest.email
            }

        assertNull(userRegistered)
    }

    @Test
    @Sql(CLEAR_ALL_TABLES, USERS)
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
            users.firstOrNull {
                it.id != null && it.name == registerUserRequest.name && it.email == registerUserRequest.email
            }

        assertNull(userRegistered)
    }

}