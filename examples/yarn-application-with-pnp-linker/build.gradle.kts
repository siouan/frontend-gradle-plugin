import java.nio.file.Files
import kotlin.io.path.Path
import org.siouan.frontendgradleplugin.infrastructure.gradle.InstallFrontendTask

plugins {
    id("org.siouan.frontend-jdk21") version "10.0.0"
}

frontend {
    nodeVersion.set("22.11.0")
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
    verboseModeEnabled.set(true)
}

tasks.named<InstallFrontendTask>("installFrontend") {
    val ciPlatformPresent = providers.environmentVariable("CI").isPresent()
    val lockFilePath = "${projectDir}/yarn.lock"
    val retainedMetadataFileNames: Set<String>
    if (ciPlatformPresent) {
        retainedMetadataFileNames = setOf(lockFilePath)
    } else {
        // The naive configuration below allows to skip the task if the last successful execution did not change neither
        // the package.json file, nor the lock file, nor the cache directory. Any other scenario where for
        // example the lock file is regenerated will lead to another execution before the task is "up-to-date" because
        // the lock file is both an input and an output of the task.
        retainedMetadataFileNames = mutableSetOf("${projectDir}/package.json")
        if (Files.exists(Path(lockFilePath))) {
            retainedMetadataFileNames.add(lockFilePath)
        }
        outputs.file(lockFilePath).withPropertyName("lockFile")
    }
    inputs.files(retainedMetadataFileNames).withPropertyName("metadataFiles")
    outputs.files("${projectDir}/.pnp.cjs").withPropertyName("pnpCjsFile")
    outputs.dir("${projectDir}/.yarn/cache").withPropertyName("yarnCacheDirectory")
}
