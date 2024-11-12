import org.siouan.frontendgradleplugin.infrastructure.gradle.ResolvePackageManagerTask
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarnTaskType

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

tasks.register<RunYarnTaskType>("yarn1Version") {
    dependsOn("installPackageManager")
    args.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("yarn1Version")
}
