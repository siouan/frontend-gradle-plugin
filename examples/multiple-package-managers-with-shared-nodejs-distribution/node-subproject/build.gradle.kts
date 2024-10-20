import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode

plugins {
    id("org.siouan.frontend-jdk11")
}

frontend {
    nodeVersion.set("20.18.0")
    nodeInstallDirectory.set(file(rootProject.ext.get("nodeInstallDirectory")!!))
}

tasks.register<RunNodeTaskType>("nodeVersion") {
    dependsOn("installNode")
    script.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("nodeVersion")
}
