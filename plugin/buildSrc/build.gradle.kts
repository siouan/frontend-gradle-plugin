plugins {
    id("java-gradle-plugin")
}

dependencies {
    implementation(gradleApi())
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
