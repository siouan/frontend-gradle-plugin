## Example: build a full-stack Spring Boot WAR application with dedicated frontend and backend sub-projects

_Note_: this example uses Groovy syntax, but can be easily translated to the Kotlin syntax using the DSL reference
introduced [here][dsl-reference]. This example shall be customized to match exactly the developer needs and contraints.

If you plan to serve your frontend with a Java backend (e.g. a [Spring Boot][spring-boot] application), you will
probably use other Gradle plugins, such as the [Gradle Java plugin][gradle-java-plugin], the
[Gradle WAR plugin][gradle-war-plugin], the [Gradle Spring Boot plugin][gradle-spring-boot-plugin], or other ones of
your choice.

In this configuration, you may package your full-stack application as a JAR/WAR artifact. To do so, the frontend must be
assembled before the backend, and generally provided in a special directory for the backend packaging task (e.g.
`jar`/`war`/`bootJar`/`bootWar`... tasks). 

This example demonstrates the following features:
- Definition of a frontend sub-project.
- Packaging a WAR artifact containing frontend artifacts built in another sub-project.

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.

### Description

The frontend project builds an `index.html` file in the `build/www` directory. A custom task named
`processFrontendResources`, launched before the `processResources` task of the backend project, copies this file in the
`${project.buildDir}/resources/main/public` directory of the backend project. The `bootWar` task in the backend project
automatically packages files in the `${project.buildDir}/resources/main/public` directory in the WAR artifact so as they
are publicly accessible. Finally, enter `gradlew build` on a command line to build the WAR artifact in the backend
project.

If you plan to build a [Spring Boot][spring-boot] application, the WAR artifact may be replaced with a bootable WAR
artifact with the [Gradle Spring Boot plugin][gradle-spring-boot-plugin] instead of the
[Gradle WAR plugin][gradle-war-plugin]. To do so, modify the [`build.gradle`](backend/build.gradle) file:

```groovy
def frontendResourcesDir = file("${project('backend').buildDir}/resources/main/public")
```

Finally:
- Enter `gradlew bootRun` on a command line.
- Open a browser, connect to URL `http://localhost:8080`, and see the following message displayed: "Hello from the
`frontend-gradle-plugin`!".

[dsl-reference]: <../../README.md#dsl-reference> (DSL reference)
[gradle-java-plugin]: <https://docs.gradle.org/current/userguide/java_plugin.html> (Gradle Java plugin)
[gradle-spring-boot-plugin]: <https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/> (Gradle Spring Boot plugin)
[gradle-war-plugin]: <https://docs.gradle.org/current/userguide/war_plugin.html> (Gradle WAR plugin)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[spring-boot]: <https://spring.io/projects/spring-boot> (Spring Boot)
