import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNodeTaskType
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmTaskType

plugins {
    id("org.siouan.frontend-jdk17")
}

//val preinstalledNodeDistributionDirectory = file(<path-to-nodejs-install-directory>);
val preinstalledNodeDistributionDirectory = null as File?;

frontend {
    nodeDistributionProvided.set(true)
    nodeInstallDirectory.set(preinstalledNodeDistributionDirectory)
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
}

tasks.register<RunNodeTaskType>("nodeVersion") {
    dependsOn("installNode")
    script.set("-v")
}

tasks.register<RunNpmTaskType>("npmVersion") {
    dependsOn("installPackageManager")
    script.set("-v")
}
