import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarnTaskType

plugins {
    alias(libs.plugins.frontend)
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
    args.set("--version")
}

tasks.named<Task>("build") {
    dependsOn("yarn3Version")
}
