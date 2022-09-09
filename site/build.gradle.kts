plugins {
    id("org.siouan.frontend-jdk11") version "6.0.0"
}

frontend {
    nodeVersion.set("16.18.0")
    yarnEnabled.set(true)
    yarnVersion.set("1.22.19")
    assembleScript.set("run generate")
    cleanScript.set("run clean")
    publishScript.set("run deploy")
    verboseModeEnabled.set(true)
}
