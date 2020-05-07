## Example: build a frontend application in a single project.

_Note_: this example uses Groovy syntax, but can be easily translated to the Kotlin syntax using the DSL reference
introduced [here][dsl-reference]. This example shall be customized to match exactly the developer needs and contraints.

This example demonstrates the following feature:
- Definition of a frontend application build using [npm][npm] or [Yarn][classic-yarn].

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.

### Description

Source code shows configuration with [npm][npm]. To use [Yarn][classic-yarn], modify the following files:

- [`build.gradle`](build.gradle)

Add the following lines after the `nodeVersion` property initialization:

```groovy
yarnEnabled = true
yarnVersion = '1.22.4'
```

- [`package.json`](package.json):

Replace `npm` executable with `yarn` executable in the `scripts` section.

Finally, enter `gradlew build` on a command line.

[classic-yarn]: <https://classic.yarnpkg.com/> (Yarn 1.x)
[dsl-reference]: <../../README.md#dsl-reference> (DSL reference)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[npm]: <https://www.npmjs.com/> (npm)
