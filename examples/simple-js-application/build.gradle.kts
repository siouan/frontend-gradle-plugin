plugins {
    id("org.siouan.frontend-jdk11")
}

frontend {
    nodeVersion.set("18.16.0")
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
}
