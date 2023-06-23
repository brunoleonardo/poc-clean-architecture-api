package br.com.blas.forum.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
abstract class BaseTest {

    lateinit var wireMockServer: WireMockServer

    @Autowired
    lateinit var mockMvc: MockMvc

    var mapper: ObjectMapper = ObjectMapper().registerKotlinModule()

    @BeforeEach
    protected fun setup() {
        wireMockServer = WireMockServer(WireMockConfiguration().port(9999))
        wireMockServer.start()
    }

    @AfterEach
    protected open fun afterEach() {
        wireMockServer.stop()
    }

    companion object {
        @Container
        val container = MySQLContainer<Nothing>(DockerImageName.parse("mysql:8.0.29"))

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
        }
    }

}