import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarnTaskType

plugins {
    id("org.siouan.frontend-jdk17")
}

frontend {
    nodeDistributionProvided.set(true)
    nodeInstallDirectory.set(file(rootProject.ext.get("nodeInstallDirectory")!!))
}

tasks.named<Task>("resolvePackageManager") {
    dependsOn(":node-subproject:installNode")
}

tasks.register<RunYarnTaskType>("yarn3Version") {
    dependsOn("installPackageManager")
    script.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("yarn3Version")
}
