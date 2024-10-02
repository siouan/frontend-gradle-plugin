pluginManagement {
    plugins {
        id("org.siouan.frontend-jdk21") version "8.2.0"
    }
}

include("node-subproject", "npm-6-subproject", "npm-9-subproject", "pnpm-6-subproject", "pnpm-8-subproject", "yarn-1-subproject", "yarn-3-subproject")
