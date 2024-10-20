import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNodeTaskType

plugins {
    id("org.siouan.frontend-jdk21")
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
