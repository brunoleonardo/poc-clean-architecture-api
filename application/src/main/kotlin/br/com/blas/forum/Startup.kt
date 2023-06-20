package br.com.blas.forum

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@OpenAPIDefinition(
    servers = [Server(url = "/")],
    info = Info(
        title = "Forum Api",
        description = "Forum Api para estudo do padrão arquitetural 'Clean Arquitecture' com 'Kotlin' :)",
        version = "1.0",
        license = License(name = "Bruno Leonardo - All Rights Reserved", url = "")
    )
)
@EnableJpaAuditing
@SpringBootApplication
class Startup

fun main(args: Array<String>) {
    runApplication<br.com.blas.forum.Startup>(*args)
}
