# Standalone frontend project build with pre-installed Node.js/Yarn distributions

This example is introduced with Groovy syntax. Translating it to the Kotlin syntax should be easy thanks to the
DSL reference introduced [here][dsl-reference].

This project is a basic example to configure such build. It shall be customized to match exactly your needs and
your contraints.

## Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in the root directory of each example.

## Use case

A standalone frontend project based on provided/shared distributions of [NPM][npm] and/or [Yarn][yarn].

## Description

Source code shows configuration with NPM. Set the install directory of the Node.js distribution in the `build.gradle`
file. To use Yarn and a pre-installed distribution instead of NPM, modify the following files:

- [`build.gradle`](build.gradle)

Add the following lines after the `nodeInstallDirectory` property:

```groovy
yarnEnabled = true
yarnDistributionProvided = true
yarnInstallDirectory = file('<path-to-yarn-install-directory')
```

- [`package.json`](package.json):

Replace `npm` executable with `yarn` executable in the `scripts` section.

[dsl-reference]: <../../README.md#dsl-reference> (DSL reference)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[npm]: <https://www.npmjs.com/> (NPM)
[yarn]: <https://yarnpkg.com/> (Yarn)
