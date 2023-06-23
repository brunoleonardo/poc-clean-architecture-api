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
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
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
    protected fun afterEach() {
        wireMockServer.stop()
    }

}