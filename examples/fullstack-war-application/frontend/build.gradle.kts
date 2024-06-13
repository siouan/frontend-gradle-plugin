plugins {
    id("org.siouan.frontend-jdk17")
}

frontend {
    nodeVersion.set("20.14.0")
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
}
