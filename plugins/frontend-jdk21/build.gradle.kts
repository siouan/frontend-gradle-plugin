import org.siouan.frontendgradleplugin.GradleTestListener

val fgpJdkVersion: String by extra
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
    id("idea")
    id("jacoco")
    alias(libs.plugins.gradle.plugin.publish)
    alias(libs.plugins.sonarqube)
}

repositories {
    mavenCentral()
}

group = fgpGroup
version = fgpVersion
description = fgpDescription

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(fgpJdkVersion)
    }
    withJavadocJar()
    withSourcesJar()
}

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
    }
}

val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
    extendsFrom(configurations.testImplementation.get())
}

configurations["integrationTestRuntimeOnly"]
    .extendsFrom(configurations.runtimeOnly.get())
    .extendsFrom(configurations.testRuntimeOnly.get())

val mockitoAgent = configurations.create("mockitoAgent")
dependencies {
    implementation(gradleApi())
    implementation(libs.resilience4j.retry.jdk17)
    implementation(libs.httpclient5)
    implementation(libs.commons.compress)
    implementation(libs.json)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.mockito.core)
    mockitoAgent(libs.mockito.core) { isTransitive = false }
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.junit.pioneer)
    testImplementation(libs.assertj.core)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    integrationTestImplementation(libs.wiremock)
}

idea {
    module {
        // Force integration test source set as test folder
        testSources.from(project.sourceSets.getByName("integrationTest").java.srcDirs)
        testResources.from(project.sourceSets.getByName("integrationTest").resources.srcDirs)
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

jacoco {
    toolVersion = libs.versions.jacoco.version.get()
}

gradlePlugin {
    website.set(fgpWebsiteUrl)
    vcsUrl.set(fgpVcsUrl)
    plugins {
        create("frontendPlugin") {
            id = "${fgpGroup}.frontend-jdk${fgpJdkVersion}"
            implementationClass = fgpImplementationClass
            displayName = fgpDisplayName
            description = fgpDescription
            tags.set(fgpGradlePluginPortalTags.split(","))
        }
    }
}

sonarqube {
    properties {
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "siouan")
        property("sonar.projectKey", "siouan_frontend-gradle-plugin")
        property("sonar.projectName", "frontend-gradle-plugin")
        property("sonar.projectVersion", "${fgpVersion}-jdk${fgpJdkVersion}")

        property("sonar.links.homepage", "https://github.com/siouan/frontend-gradle-plugin")
        property("sonar.links.ci", "https://github.com/siouan/frontend-gradle-plugin/actions")
        property("sonar.links.scm", "https://github.com/siouan/frontend-gradle-plugin")
        property("sonar.links.issue", "https://github.com/siouan/frontend-gradle-plugin/issues")

        property("sonar.sources", "src/main")
        property("sonar.tests", "src/test,src/integrationTest")

        property("sonar.java.binaries", "build/classes/java/main")
        property("sonar.java.test.binaries", "build/classes/java/test,build/classes/java/integrationTest")
        property("sonar.junit.reportPaths", "build/test-results/test/,build/test-results/integrationTest/")
        property("sonar.jacoco.xmlReportPaths", "build/reports/jacoco/report.xml")
        property("sonar.verbose", true)

        // Irrelevant duplications detected on task inputs
        property(
            "sonar.cpd.exclusions",
            "**/org/siouan/frontendgradleplugin/domain/Resolve*ExecutablePath.java,**/org/siouan/frontendgradleplugin/infrastructure/gradle/FrontendExtension.java"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<Test>("test") {
    jvmArgs(
        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
        "--add-opens", "java.base/java.util=ALL-UNNAMED",
        "-javaagent:${mockitoAgent.asPath}"
    )
    outputs.upToDateWhen { false }
}

tasks.register<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    // Yarn immutable installs prevents failures in integration tests due to missing yarn.lock file.
    environment["YARN_ENABLE_IMMUTABLE_INSTALLS"] = "false"
    shouldRunAfter("test")
    outputs.upToDateWhen { false }
}

tasks.named<Task>("check") {
    dependsOn("integrationTest")
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn("test", "integrationTest")
    executionData.setFrom(
        project.layout.buildDirectory.files("jacoco/test.exec", "jacoco/integrationTest.exec"),
    )
    reports {
        xml.required.set(true)
        xml.outputLocation.set(project.layout.buildDirectory.file("reports/jacoco/report.xml"))
    }
}
