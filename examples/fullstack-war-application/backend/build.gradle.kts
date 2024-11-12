plugins {
    id("java")
    id("war")
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.register<Copy>("processFrontendResources") {
    // Directory containing the artifacts produced by the frontend project
    val frontendProjectBuildDir = project(":frontend").layout.buildDirectory
    val frontendBuildDir = frontendProjectBuildDir.dir("www")
    // Directory where the frontend artifacts must be copied to be packaged alltogether with the backend by the 'war'
    // plugin.
    val frontendResourcesDir = project.layout.buildDirectory.dir("resources/main/public")

    group = "Frontend"
    description = "Process frontend resources"
    dependsOn(":frontend:assembleFrontend")

    from(frontendBuildDir)
    into(frontendResourcesDir)
}

tasks.named<Task>("processResources") {
    dependsOn("processFrontendResources")
}
