pluginManagement {
    plugins {
        id("java")
        id("war")
        id("org.springframework.boot") version "3.3.4"
        id("io.spring.dependency-management") version "1.1.6"
        id("org.siouan.frontend-jdk17") version "9.1.0"
    }
}

include("backend", "frontend")
