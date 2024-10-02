buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.siouan", "frontend-jdk21", "8.2.0")
    }
}

plugins {
    id("org.siouan.frontend-jdk21")
}

frontend {
    nodeVersion.set("20.14.0")
    assembleScript.set("run generate")
    cleanScript.set("run clean")
    publishScript.set("run deploy")
    verboseModeEnabled.set(true)
}
