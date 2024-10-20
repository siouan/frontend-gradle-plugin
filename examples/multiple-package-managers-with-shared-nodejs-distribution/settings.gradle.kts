pluginManagement {
    plugins {
        id("org.siouan.frontend-jdk21") version "9.0.0"
    }
}

include("node-subproject", "npm-6-subproject", "npm-10-subproject", "pnpm-6-subproject", "pnpm-9-subproject", "yarn-1-subproject", "yarn-4-subproject")
