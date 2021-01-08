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
- Packaging a Spring Boot WAR artifact containing frontend artifacts built in another sub-project.

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.

### Description

The frontend sub-project builds an `index.html` file in its `build/www` directory. A custom task named
`processFrontendResources`, and defined in the backend sub-project copies the previous file in the
`build/resources/main/public` directory of the backend sub-project. The `processResources` task of the backend
sub-project depends on this `processFrontendResources` task, to ensure frontend artifacts are included when building
the WAR artifact: the `bootWar` task in the backend project automatically packages files in the
`${project.buildDir}/resources/main/public` directory into the WAR artifact so as they are publicly accessible.

Finally:
- Enter `gradlew bootRun` on a command line.
- Open a browser, connect to URL `http://localhost:8080`, and see the following message displayed: "Hello from the
`frontend-gradle-plugin`!".

[dsl-reference]: <https://siouan.github.io/frontend-gradle-plugin/configuration> (DSL reference)
[gradle-java-plugin]: <https://docs.gradle.org/current/userguide/java_plugin.html> (Gradle Java plugin)
[gradle-spring-boot-plugin]: <https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/> (Gradle Spring Boot plugin)
[gradle-war-plugin]: <https://docs.gradle.org/current/userguide/war_plugin.html> (Gradle WAR plugin)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[spring-boot]: <https://spring.io/projects/spring-boot> (Spring Boot)
