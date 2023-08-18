buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.siouan", "frontend-jdk11", "7.1.0")
    }
}

plugins {
    id("org.siouan.frontend-jdk11")
}

frontend {
    nodeVersion.set("18.17.1")
    assembleScript.set("run generate")
    cleanScript.set("run clean")
    publishScript.set("run deploy")
    verboseModeEnabled.set(true)
}
