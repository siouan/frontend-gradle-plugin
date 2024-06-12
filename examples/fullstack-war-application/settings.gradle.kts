pluginManagement {
    plugins {
        id("java")
        id("war")
        id("org.springframework.boot") version "2.7.12"
        id("io.spring.dependency-management") version "1.1.0"
        id("org.siouan.frontend-jdk17") version "8.1.0"
    }
}

include("backend", "frontend")
