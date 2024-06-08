plugins {
    id("java-gradle-plugin")
}

dependencies {
    implementation(gradleApi())
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
