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
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.apache.commons:commons-compress:1.20")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.0")
    testImplementation("org.mockito:mockito-core:3.6.0")
    testImplementation("org.mockito:mockito-junit-jupiter:3.6.0")
    testImplementation("org.assertj:assertj-core:3.18.0")
    testImplementation("com.github.tomakehurst:wiremock:2.27.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
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
