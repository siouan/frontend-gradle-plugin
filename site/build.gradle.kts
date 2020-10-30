plugins {
    id("org.siouan.frontend-jdk11") version "4.0.0"
}

frontend {
    nodeVersion.set("14.15.0")
    yarnEnabled.set(true)
    yarnVersion.set("1.22.5")
    assembleScript.set("run generate")
    cleanScript.set("run clean")
    publishScript.set("run deploy")
}
