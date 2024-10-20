import org.siouan.frontendgradleplugin.infrastructure.gradle.ResolvePackageManagerTask
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpmTaskType

plugins {
    id("org.siouan.frontend-jdk21")
}

frontend {
    nodeDistributionProvided.set(true)
    nodeInstallDirectory.set(file(rootProject.ext.get("nodeInstallDirectory")!!))
}

tasks.named<ResolvePackageManagerTask>("resolvePackageManager") {
    dependsOn(":node-subproject:installNode")
}

tasks.register<RunPnpmTaskType>("pnpm8Version") {
    dependsOn("installPackageManager")
    script.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("pnpm8Version")
}
