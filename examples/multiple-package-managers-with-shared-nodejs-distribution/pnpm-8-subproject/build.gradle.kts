import org.siouan.frontendgradleplugin.infrastructure.gradle.ResolvePackageManagerTask
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpm

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

tasks.register<RunPnpm>("pnpm8Version") {
    dependsOn("installPackageManager")
    script.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("pnpm8Version")
}
