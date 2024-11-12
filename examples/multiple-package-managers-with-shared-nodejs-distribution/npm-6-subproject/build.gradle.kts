import org.siouan.frontendgradleplugin.infrastructure.gradle.ResolvePackageManagerTask
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmTaskType

plugins {
    alias(libs.plugins.frontend)
}

frontend {
    nodeDistributionProvided.set(true)
    nodeInstallDirectory.set(file(rootProject.ext.get("nodeInstallDirectory")!!))
}

tasks.named<ResolvePackageManagerTask>("resolvePackageManager") {
    dependsOn(":node-subproject:installNode")
}

tasks.register<RunNpmTaskType>("npm6Version") {
    dependsOn("installPackageManager")
    args.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("npm6Version")
}
