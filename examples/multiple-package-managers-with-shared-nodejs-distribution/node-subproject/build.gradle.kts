import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNodeTaskType

plugins {
    alias(libs.plugins.frontend)
}

frontend {
    nodeVersion.set("22.11.0")
    nodeInstallDirectory.set(file(rootProject.ext.get("nodeInstallDirectory")!!))
}

tasks.register<RunNodeTaskType>("nodeVersion") {
    dependsOn("installNode")
    args.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("nodeVersion")
}
