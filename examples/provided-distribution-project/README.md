# Standalone frontend project build with pre-installed Node.js/Yarn distributions

_Notes_: this example is introduced with Groovy syntax. Translating it to the Kotlin syntax should be easy thanks to the
DSL reference introduced [here][dsl-reference]. This is a basic example to configure such build. It shall be customized
to match exactly the developer needs and contraints.

Such example demonstrates the following features:

- Definition of a frontend project using [npm][npm] or [Yarn][yarn].
- Using preinstalled distributions [Node.js][nodejs] and/or [Yarn][yarn] as provided runtimes.

## Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in the root directory of each example.

## Use case

A standalone frontend project based on provided/shared distributions of [Node.js][nodejs] and/or [Yarn][yarn].

## Description

Source code shows configuration with [npm][npm]. Set the install directory of the [Node.js][nodejs] distribution in the
`build.gradle` file. To use [Yarn][yarn] and a pre-installed distribution instead of npm, modify the following files:

- [`build.gradle`](build.gradle)

Add the following lines after the `nodeInstallDirectory` property initialization:

```groovy
yarnEnabled = true
yarnDistributionProvided = true
yarnInstallDirectory = file('<path-to-yarn-install-directory')
```

- [`package.json`](package.json):

Replace `npm` executable with `yarn` executable in the `scripts` section.

[dsl-reference]: <../../README.md#dsl-reference> (DSL reference)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[nodejs]: <https://nodejs.org/> (Node.js)
[npm]: <https://www.npmjs.com/> (npm)
[yarn]: <https://yarnpkg.com/> (Yarn)
