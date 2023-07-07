import java.nio.file.Files
import kotlin.io.path.Path
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallFrontendTask

plugins {
    id("org.siouan.frontend-jdk11")
}

frontend {
    nodeVersion.set("18.16.0")
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
    verboseModeEnabled.set(true)
}

tasks.named<InstallFrontendTask>("installFrontend") {
    val ciPlatformPresent = providers.environmentVariable("CI").isPresent()
    val lockFilePath = "${projectDir}/package-lock.json"
    val retainedMetadataFileNames: Set<String>
    if (ciPlatformPresent) {
        // If the host is a CI platform, execute a strict install of dependencies based on the lock file.
        installScript.set("ci")
        retainedMetadataFileNames = setOf(lockFilePath)
    } else {
        // The naive configuration below allows to skip the task if the last successful execution did not change neither
        // the package.json file, nor the package-lock.json file, nor the node_modules directory. Any other scenario
        // where for example the lock file is regenerated will lead to another execution before the task is "up-to-date"
        // because the lock file is both an input and an output of the task.
        val acceptableMetadataFileNames = listOf(lockFilePath, "${projectDir}/yarn.lock")
        retainedMetadataFileNames = mutableSetOf("${projectDir}/package.json")
        for (acceptableMetadataFileName in acceptableMetadataFileNames) {
            if (Files.exists(Path(acceptableMetadataFileName))) {
                retainedMetadataFileNames.add(acceptableMetadataFileName)
                break
            }
        }
        outputs.file(lockFilePath).withPropertyName("lockFile")
    }
    inputs.files(retainedMetadataFileNames).withPropertyName("metadataFiles")
    outputs.dir("${projectDir}/node_modules").withPropertyName("nodeModulesDirectory")
}
