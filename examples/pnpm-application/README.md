## Example: build a pnpm application

This example demonstrates how to configure a Gradle project to install a [Node.js][nodejs] distribution, and build a
Javascript application with [pnpm][pnpm].

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.

### Description

- [`settings.gradle.kts`](settings.gradle.kts): defines version of used plugins.
- [`build.gradle.kts`](build.gradle.kts): applies and configure this plugin.
- [`package.json`](package.json): scripts connected to Gradle lifecycle tasks `clean`, `assemble`, `check`.

[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[nodejs]: <https://nodejs.org/> (Node.js)
[pnpm]: <https://pnpm.io/> (pnpm)
