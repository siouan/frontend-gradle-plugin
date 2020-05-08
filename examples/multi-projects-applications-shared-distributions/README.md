## Example: build multiple frontend applications with dedicated sub-projects using shared distributions

_Note_: this example uses Groovy syntax, but can be easily translated to the Kotlin syntax using the DSL reference
introduced [here][dsl-reference]. This example shall be customized to match exactly the developer needs and contraints.

This example demonstrates the following features:
- Definition of a frontend sub-project.
- Customized location of downloaded distributions.
- Sharing the same [Node.js][nodejs] distribution between multi sub-projects.

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.

### Description

The `npm-frontend` project is the 'master' of the [Node.js][nodejs] distribution, installed in the directory of the root
project. The `yarn-frontend` project is the 'master' of the [Yarn][classic-yarn] distribution, also installed in the directory
of the root project, and the project uses the [Node.js][nodejs] distribution _provided_ by the `npm-frontend` project.
The `yarn-frontend:installFrontend` task depends on the `npm-frontend:installNode` to ensure the [Node.js][nodejs]
distribution is installed if one of the `yarn-frontend` project's task is executed directly.

Finally, enter `gradlew build` on a command line.

[classic-yarn]: <https://classic.yarnpkg.com/> (Yarn 1.x)
[dsl-reference]: <../../README.md#dsl-reference> (DSL reference)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[nodejs]: <https://nodejs.org/> (Node.js)
[npm]: <https://www.npmjs.com/> (npm)
