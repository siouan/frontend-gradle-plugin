import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm

plugins {
    id("org.siouan.frontend-jdk21")
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

tasks.register<RunNode>("nodeVersion") {
    dependsOn("installNode")
    script.set("-v")
}

tasks.register<RunNpm>("npmVersion") {
    dependsOn("installPackageManager")
    script.set("-v")
}
