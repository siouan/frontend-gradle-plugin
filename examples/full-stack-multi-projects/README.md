# Full-stack multi projects build

_Notes_: this example is introduced with Groovy syntax. Translating it to the Kotlin syntax should be easy thanks to the
DSL reference introduced [here][dsl-reference]. This is a basic example to configure such build. It shall be customized
to match exactly the developer needs and contraints.

If you plan to serve your frontend with a Java backend (e.g. a [Spring Boot][spring-boot] application), you will
probably use other Gradle plugins, such as the [Gradle Java plugin][gradle-java-plugin], the
[Gradle WAR plugin][gradle-war-plugin], the [Gradle Spring Boot plugin][gradle-spring-boot-plugin], or other ones of
your choice.

In this configuration, you may package your full-stack application as a JAR/WAR artifact. To do so, the frontend must be
assembled before the backend, and generally provided in a special directory for the backend packaging task (e.g.
`jar`/`war`/`bootJar`/`bootWar`... tasks). 

Such example demonstrates the following features:

- Definition of a frontend sub-project.
- Packaging a Java artifact containing frontend artifacts built in another sub-project.

## Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in the root directory of each example.

## Use case

A root project composed of a java backend project, a frontend project based on NPM, and packaged together in a
full-stack WAR artifact.

## Description

The frontend project builds an `index.html` file in the `build/www` directory. A custom task named
`processFrontendResources`, launched before the `processResources` task of the backend project, copies this file in the
`src/main/webapp` directory of the backend project. The `war` task in the backend project automatically packages files
in the `src/main/webapp` directory at the root of the WAR artifact so as they are publicly accessible. Finally, enter
`gradlew build` on a command line to build the WAR artifact in the backend project.

If you plan to build a [Spring Boot][spring-boot] application, the WAR artifact may be replaced by a bootable WAR
artifact with the [Gradle Spring Boot plugin][gradle-spring-boot-plugin] instead of the
[Gradle WAR plugin][gradle-war-plugin]. To do so, modify the [`build.gradle`](build.gradle) file:

```groovy
def frontendResourcesDir = file("${project('backend').buildDir}/resources/main/public")
```

[dsl-reference]: <../../README.md#dsl-reference> (DSL reference)
[gradle-java-plugin]: <https://docs.gradle.org/current/userguide/java_plugin.html> (Gradle Java plugin)
[gradle-spring-boot-plugin]: <https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/> (Gradle Spring Boot plugin)
[gradle-war-plugin]: <https://docs.gradle.org/current/userguide/war_plugin.html> (Gradle WAR plugin)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[spring-boot]: <https://spring.io/projects/spring-boot> (Spring Boot)
