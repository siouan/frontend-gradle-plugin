import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNodeTaskType
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmTaskType

plugins {
    id("org.siouan.frontend-jdk21") version "10.0.0"
}

//val preinstalledNodeDistributionDirectory = file(<path-to-nodejs-install-directory>);
val preinstalledNodeDistributionDirectory = null as File?;

frontend {
    nodeDistributionProvided.set(true)
    nodeInstallDirectory.set(preinstalledNodeDistributionDirectory)
    assembleScript.set("run build")
    checkScript.set("run check")
}

tasks.register<RunNodeTaskType>("nodeVersion") {
    dependsOn("installNode")
    args.set("-v")
}

tasks.register<RunNpmTaskType>("npmVersion") {
    dependsOn("installPackageManager")
    args.set("-v")
}
