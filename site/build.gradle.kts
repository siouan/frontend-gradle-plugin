buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.siouan", "frontend-jdk11", "9.1.0")
    }
}

plugins {
    id("org.siouan.frontend-jdk11")
}

frontend {
    nodeVersion.set("20.18.0")
    assembleScript.set("run generate")
    cleanScript.set("run clean")
    publishScript.set("run deploy")
    verboseModeEnabled.set(true)
}
