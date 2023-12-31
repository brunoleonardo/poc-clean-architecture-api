plugins {
    id("org.springframework.boot") version "2.7.13" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
    kotlin("plugin.jpa") version "1.9.0"
    id("org.sonarqube") version "3.3" apply true
}

group = "br.com.blas"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Faz com que a configuração dos arquivos do projeto raiz dependa de cada subprojeto
    subprojects.forEach {
        implementation(it)
    }
}
