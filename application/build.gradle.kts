import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("kotlin-jpa")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("jacoco")
}

group = "br.com.blas"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2021.0.3"

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(null, "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
    implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.9")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.9")
    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.9")
    implementation("org.springframework:spring-jms")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.flywaydb:flyway-mysql")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.6.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.amazonaws:amazon-sqs-java-messaging-lib:2.0.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")
    compileOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.25.1")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    group = "Reporting"
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = true
        html.destination = file("$buildDir/reports/coverage")

    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
    violationRules {
        rule {
            limit {
                minimum = "0.0".toBigDecimal()
                value = "COVEREDRATIO"
                counter = "LINE"
            }
        }
    }
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")
    useJUnitPlatform()
}

springBoot {
    mainClass.set("br.com.blas.forum.StartupKt")
}