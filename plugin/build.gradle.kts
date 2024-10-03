import org.siouan.frontendgradleplugin.GradleTestListener

val fgpArtifactId: String by extra
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
    id("com.gradle.plugin-publish")
    id("org.sonarqube")
}

repositories {
    mavenCentral()
}

group = fgpGroup
version = fgpVersion
description = fgpDescription

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
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

dependencies {
    implementation(gradleApi())
    implementation("io.github.resilience4j:resilience4j-retry:2.2.0")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.4")
    implementation("org.apache.commons:commons-compress:1.27.1")
    implementation("org.json:json:20240303")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.1")
    testImplementation("org.mockito:mockito-core:5.14.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.1")
    testImplementation("org.junit-pioneer:junit-pioneer:2.2.0")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.1")
    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")

    integrationTestImplementation("org.wiremock:wiremock:3.9.1")
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
    toolVersion = "0.8.12"
}

gradlePlugin {
    website.set(fgpWebsiteUrl)
    vcsUrl.set(fgpVcsUrl)
    plugins {
        create("frontendPlugin") {
            id = "$fgpGroup.$fgpArtifactId"
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
        property("sonar.projectVersion", "${fgpVersion}-jdk21")

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

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<Test>("test") {
    jvmArgs(
        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
        "--add-opens", "java.base/java.util=ALL-UNNAMED"
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
    dependsOn(tasks.named("integrationTest"))
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"), tasks.named("integrationTest"))
    executionData.setFrom(
        project.layout.buildDirectory.files("jacoco/test.exec", "jacoco/integrationTest.exec"),
    )
    reports {
        xml.required.set(true)
        xml.outputLocation.set(project.layout.buildDirectory.file("reports/jacoco/report.xml"))
    }
}

gradle.addListener(GradleTestListener(logger))
