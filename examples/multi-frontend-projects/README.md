# Multi frontend projects build

_Notes_: this example is introduced with Groovy syntax. Translating it to the Kotlin syntax should be easy thanks to the
DSL reference introduced [here][dsl-reference]. This is a basic example to configure such build. It shall be customized
to match exactly the developer needs and contraints.

This example demonstrates the following features:

- Definition of a frontend sub-project.
- Customized location of downloaded distributions.
- Sharing the same [Node.js][nodejs] distribution between multi projects.

## Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in the root directory of each example.

## Use case

A root project composed of a first frontend project based on [npm][npm], and a second frontend project based on
[Yarn][yarn]. Both frontend projects share the same [Node.js][nodejs] distribution.

## Description

The `npm-frontend` project is the 'master' of the [Node.js][nodejs] distribution, installed in the directory of the root
project. The `yarn-frontend` project is the 'master' of the [Yarn][yarn] distribution, also installed in the directory
of the root project, and the project uses the [Node.js][nodejs] distribution _provided_ by the `npm-frontend` project.
The `yarn-frontend:installFrontend` task depends on the `npm-frontend:installNode` to ensure the [Node.js][nodejs]
distribution is installed if one of the `yarn-frontend` project's task is executed directly.

[dsl-reference]: <../../README.md#dsl-reference> (DSL reference)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[nodejs]: <https://nodejs.org/> (Node.js)
[npm]: <https://www.npmjs.com/> (npm)
[yarn]: <https://yarnpkg.com/> (Yarn)
