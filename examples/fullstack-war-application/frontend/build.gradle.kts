plugins {
    id("org.siouan.frontend-jdk21") version "10.0.0"
}

frontend {
    nodeVersion.set("22.11.0")
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
}
