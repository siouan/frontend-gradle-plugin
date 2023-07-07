## Example: build a simple JS application using a preinstalled [Node.js][nodejs] distribution.

This example demonstrates how to configure a Gradle project to build a Javascript application.

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.
- Preinstalled distribution of [Node.js][nodejs] on the workstation.
- Either set the value of the `preinstalledNodeDistributionDirectory` variable in the
[`build.gradle.kts`](build.gradle.kts) file, or define a `FGP_NODEJS_HOME` environment variable pointing to the
[Node.js][nodejs] installation directory.

### Description

- [`settings.gradle.kts`](settings.gradle.kts): defines version of used plugins.
- [`build.gradle.kts`](build.gradle.kts): applies and configures the plugin.
- [`package.json`](package.json): scripts connected to Gradle lifecycle tasks `clean`, `assemble`, `check`.

Enter `gradlew nodeVersion npmVersion build` on a command line and verify outputs of each task.

[dsl-reference]: <https://siouan.github.io/frontend-gradle-plugin/configuration> (DSL reference)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[nodejs]: <https://nodejs.org/> (Node.js)
