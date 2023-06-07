plugins {
    id("java")
    id("war")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
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
    val frontendProjectBuildDir = project(":frontend").buildDir
    val frontendBuildDir = file("${frontendProjectBuildDir}/www")
    // Directory where the frontend artifacts must be copied to be packaged alltogether with the backend by the 'war'
    // plugin.
    val frontendResourcesDir = file("${project.buildDir}/resources/main/public")

    group = "Frontend"
    description = "Process frontend resources"
    dependsOn(":frontend:assembleFrontend")

    from(frontendBuildDir)
    into(frontendResourcesDir)
}

tasks.named<Task>("processResources") {
    dependsOn("processFrontendResources")
}
