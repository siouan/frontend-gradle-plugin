import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode

plugins {
    id("org.siouan.frontend-jdk17")
}

frontend {
    nodeVersion.set("20.14.0")
    nodeInstallDirectory.set(file(rootProject.ext.get("nodeInstallDirectory")!!))
}

tasks.register<RunNode>("nodeVersion") {
    dependsOn("installNode")
    script.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("nodeVersion")
}
