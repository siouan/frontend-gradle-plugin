# Standalone frontend project build

_Notes_: this example is introduced with Groovy syntax. Translating it to the Kotlin syntax should be easy thanks to the
DSL reference introduced [here][dsl-reference]. This is a basic example to configure such build. It shall be customized
to match exactly the developer needs and contraints.

Such example demonstrates the following features:

- Definition of a frontend project using [npm][npm] or [Yarn][yarn].

## Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in the root directory of each example.

## Use case

A standalone frontend project based on [npm][npm] or [Yarn][yarn].

## Description

Source code shows configuration with [npm][npm]. To use [Yarn][yarn], modify the following files:

- [`build.gradle`](build.gradle)

Add the following lines after the `nodeVersion` property initialization:

```groovy
yarnEnabled = true
yarnVersion = '1.22.4'
```

- [`package.json`](package.json):

Replace `npm` executable with `yarn` executable in the `scripts` section.

[dsl-reference]: <../../README.md#dsl-reference> (DSL reference)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[npm]: <https://www.npmjs.com/> (npm)
[yarn]: <https://yarnpkg.com/> (Yarn)
