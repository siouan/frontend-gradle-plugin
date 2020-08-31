import org.siouan.frontendgradleplugin.GradleTestListener

val fgpGroup: String by extra
val fgpVersion: String by extra
val fgpDisplayName: String by extra
val fgpDescription: String by extra
val fgpPluginId: String by extra
val fgpImplementationClass: String by extra
val fgpWebsiteUrl: String by extra
val fgpVcsUrl: String by extra
val fgpGradlePluginPortalTags: String by extra

plugins {
    id("java-gradle-plugin")
    id("jacoco")
    id("com.gradle.plugin-publish")
    id ("maven-publish")
}

repositories {
    jcenter()
}

group = fgpGroup
version = fgpVersion
description = fgpDescription

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
        xml.setDestination(file("${buildDir}/reports/jacoco/report.xml"))
    }
}

gradlePlugin {
    plugins {
        create("frontendPlugin") {
            id = fgpPluginId
            implementationClass = fgpImplementationClass
        }
    }
}

pluginBundle {
    website = fgpWebsiteUrl
    vcsUrl = fgpVcsUrl

    (plugins) {
        "frontendPlugin" {
            displayName = fgpDisplayName
            description = fgpDescription
            tags = fgpGradlePluginPortalTags.split(",")
        }
    }
}
