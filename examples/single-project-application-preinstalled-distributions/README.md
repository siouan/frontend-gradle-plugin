## Example: build a frontend application in a single project, using preinstalled distributions.

_Note_: this example uses Groovy syntax, but can be easily translated to the Kotlin syntax using the DSL reference
introduced [here][dsl-reference]. This example shall be customized to match exactly the developer needs and contraints.

This example demonstrates the following feature:
- Definition of a frontend application build using [npm][npm] or [Yarn][classic-yarn].
- Using preinstalled distributions [Node.js][nodejs] and/or [Yarn][classic-yarn] as provided runtimes.

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.
- Preinstalled distributions of [Node.js][nodejs] and/or [Yarn][classic-yarn] on the workstation.
- Sets the value of the `preinstalledNodeDistributionDirectory` variable in the [`build.gradle`](build.gradle) file.

### Description

Source code shows configuration with [npm][npm]. Set the install directory of the [Node.js][nodejs] distribution in the
`build.gradle` file. To use [Yarn][classic-yarn] and a pre-installed distribution instead of npm, modify the following files:

- [`build.gradle`](build.gradle)

Add the following lines after the `nodeInstallDirectory` property initialization:

```groovy
yarnEnabled = true
yarnDistributionProvided = true
yarnInstallDirectory = file('<path-to-yarn-install-directory')
```

- [`package.json`](package.json):

Replace `npm` executable with `yarn` executable in the `scripts` section.

Finally, enter `gradlew build` on a command line.

[classic-yarn]: <https://classic.yarnpkg.com/> (Yarn 1.x)
[dsl-reference]: <../../README.md#dsl-reference> (DSL reference)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[nodejs]: <https://nodejs.org/> (Node.js)
[npm]: <https://www.npmjs.com/> (npm)
