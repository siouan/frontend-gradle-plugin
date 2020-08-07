import org.siouan.frontendgradleplugin.GradleTestListener

plugins {
    id("java-gradle-plugin")
    id("jacoco")
    id("com.gradle.plugin-publish")
    id ("maven-publish")
}

repositories {
    jcenter()
    mavenCentral()
}

group = "org.siouan"
version = "3.0.1-SNAPSHOT"
description = "Build Javascript-based applications with node, npm, yarn."

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(gradleApi())
    implementation("org.apache.commons:commons-compress:1.20")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.2")
    testImplementation("org.mockito:mockito-core:3.4.6")
    testImplementation("org.mockito:mockito-junit-jupiter:3.4.6")
    testImplementation("org.assertj:assertj-core:3.16.1")
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.27.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    gradle.addListener(GradleTestListener(logger))
}

tasks.named<JacocoReport>("jacocoTestReport") {
    reports {
        xml.setEnabled(true)
        xml.setDestination(file("${projectDir}/reports/jacoco/report.xml"))
    }
}

gradlePlugin {
    plugins {
        create("frontend") {
            id = "org.siouan.frontend"
            implementationClass = "org.siouan.frontendgradleplugin.FrontendGradlePlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/siouan/frontend-gradle-plugin"
    vcsUrl = "https://github.com/siouan/frontend-gradle-plugin.git"

    (plugins) {
        "frontend" {
            displayName = "Frontend Gradle plugin"
            description = "Build Javascript-based applications with node, npm, yarn."
            tags = listOf("node", "nodejs", "npm", "npx", "yarn", "frontend")
        }
    }
}
