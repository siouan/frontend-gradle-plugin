plugins {
    id("org.siouan.frontend-jdk8") version "3.0.1"
}

frontend {
    nodeVersion.set("12.18.3")
    yarnEnabled.set(true)
    yarnVersion.set("1.22.4")
    assembleScript.set("run generate")
    cleanScript.set("run clean")
    publishScript.set("run deploy")
}
