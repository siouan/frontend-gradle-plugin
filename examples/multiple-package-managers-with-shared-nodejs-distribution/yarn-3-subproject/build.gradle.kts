import org.siouan.frontendgradleplugin.infrastructure.gradle.ResolvePackageManagerTask
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarn

plugins {
    id("org.siouan.frontend-jdk11")
}

frontend {
    nodeDistributionProvided.set(true)
    nodeInstallDirectory.set(file(rootProject.ext.get("nodeInstallDirectory")!!))
}

tasks.named<Task>("resolvePackageManager") {
    dependsOn(":node-subproject:installNode")
}

tasks.register<RunYarn>("yarn3Version") {
    dependsOn("installPackageManager")
    script.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("yarn3Version")
}
