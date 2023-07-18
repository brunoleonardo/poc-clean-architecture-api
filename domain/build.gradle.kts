import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("jacoco")
}

group = "br.com.blas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("javax.inject:javax.inject:1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation(kotlin("test"))
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.test {
    useJUnitPlatform()
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

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}