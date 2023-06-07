pluginManagement {
    plugins {
        id("java")
        id("war")
        id("org.springframework.boot") version "2.7.12"
        id("io.spring.dependency-management") version "1.1.0"
        id("org.siouan.frontend-jdk11") version "7.0.0"
    }
}

include("backend", "frontend")
