plugins {
    id("org.siouan.frontend-jdk11") version "5.3.0"
}

frontend {
    nodeVersion.set("14.17.3")
    yarnEnabled.set(true)
    yarnVersion.set("1.22.10")
    assembleScript.set("run generate")
    cleanScript.set("run clean")
    publishScript.set("run deploy")
}
