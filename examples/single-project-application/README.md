## Example: build a frontend application in a single project.

_Note_: this example uses Groovy syntax, but can be easily translated to the Kotlin syntax using the DSL reference
introduced [here][dsl-reference]. This example shall be customized to match exactly the developer needs and contraints.

This example demonstrates the following feature:
- Definition of a frontend application build using [npm][npm] or [Yarn][classic-yarn].

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.

### Description

Source code shows configuration with [npm][npm]. To use [Yarn][yarn], modify the following files:

- [`build.gradle`](build.gradle)

Add the following lines after the `nodeVersion` property initialization:

```groovy
yarnEnabled = true
yarnVersion = '3.0.0'
```

- [`package.json`](package.json):

Replace `npm` executable with `yarn` executable in the `scripts` section.

Finally, enter `gradlew build` on a command line.

[dsl-reference]: <https://siouan.github.io/frontend-gradle-plugin/configuration> (DSL reference)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[npm]: <https://www.npmjs.com/> (npm)
[yarn]: <https://yarnpkg.com/> (Yarn)
