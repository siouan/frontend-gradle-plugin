plugins {
    id("org.siouan.frontend-jdk17")
}

frontend {
    nodeVersion.set("18.17.1")
    assembleScript.set("run build")
    cleanScript.set("run clean")
    checkScript.set("run check")
}
