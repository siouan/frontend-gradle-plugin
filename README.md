<h2 align="center">Frontend Gradle plugin - Integrated <a href="https://nodejs.org/" title="Node.js">Node.js</a>, <a href="https://www.npmjs.com/" title="npm">npm</a>, <a href="https://yarnpkg.com/" title="Yarn">Yarn</a> builds</h2> 
<p align="center">
    <a href="https://github.com/Siouan/frontend-gradle-plugin/releases/tag/v2.1.0"><img src="https://img.shields.io/badge/Latest%20release-2.1.0-blue.svg" alt="Latest release 2.1.0"/></a>
    <a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/License-Apache%202.0-green.svg" alt="License Apache 2.0"/></a>
    <br/>
    <a href="https://travis-ci.com/Siouan/frontend-gradle-plugin"><img src="https://travis-ci.com/Siouan/frontend-gradle-plugin.svg?branch=2.0" alt="Build status"/></a>
    <a href="https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin"><img src="https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=alert_status" alt="Quality gate status"/></a>
    <a href="https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin"><img src="https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=coverage" alt="Code coverage"/></a>
    <a href="https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin"><img src="https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=reliability_rating" alt="Reliability"/></a>
</p>

This plugin allows to integrate a [Node.js][nodejs]/[npm][npm]/[Yarn][yarn] build into Gradle. It is inspired by the
philosophy of the [frontend-maven-plugin][frontend-maven-plugin]. See the [quick start guide](#quick-start-guide) below
to install/configure the plugin, and build your frontend application.

#### Key features

- **Distribution management**: the plugin downloads and installs a [Node.js][nodejs] distribution and/or a [Yarn][yarn]
distribution when required. Optionally, a shared/global distribution may be used instead to avoid network overhead and
duplication. The plugin may also use a HTTP proxy server for downloads to take advantage of any caching facility and
submit to the organization's security rules.
- **Configurable dependencies installation**: depending on the environment, installing frontend dependencies using the
`package.json` file may require a different command (e.g. `npm ci`).
- **Built-in tasks**: no need to define tasks to build, clean, check, or publish the frontend application through
Gradle lifecycle. The plugin provides them out of the box, and ensures their implementation matches Gradle's
[recommendations][gradle-task-configuration-avoidance]. Plug scripts from a `package.json` file with the DSL, and run
`gradlew build`.
- **Customization**: for more complex use cases, the plugin provides types to create tasks and run custom commands with
[Node.js][nodejs], [npm][npm], [npx][npx], [Yarn][yarn].

#### Under the hood

- **Lazy configuration**: tasks configuration is delayed until necessary thanks to the use of Gradle
[lazy configuration API][gradle-lazy-configuration], to optimize performance of builds and ease tasks I/O chaining.
- **Self-contained domain design**: the plugin design is influenced by [clean coding][clean-coder] principles.
Implementing a domain layer isolated from any framework and infrastructure eases maintenance, simplifies writing
cross-platform unit tests. Code coverage and predictability increase.

<p align="center">
<a href="https://gradle.org/" title="Gradle"><img src="resources/gradle-icon.png" alt="Gradle icon"/></a>
<img src="resources/bullet.png" alt="Bullet"/>
<a href="https://nodejs.org/" title="Node.js"><img src="resources/nodejs-icon.png" alt="Node.js icon"/></a>
<img src="resources/bullet.png" alt="Bullet"/>
<a href="https://www.npmjs.com/" title="npm"><img src="resources/npm-icon.png" alt="npm icon"/></a>
<img src="resources/bullet.png" alt="Bullet"/>
<a href="https://yarnpkg.com/" title="Yarn"><img src="resources/yarn-icon.png" alt="Yarn icon"/></a>
</p>

## Summary

- [Quick start guide](#quick-start-guide)
  - [Requirements](#requirements)
  - [Installation](#installation)
  - [Configuration](#configuration)
    - [DSL reference](#dsl-reference)
    - [Examples][examples]
        - [Standalone frontend project build][example-standalone-frontend-project]
        - [Standalone frontend project build with preinstalled Node.js/Yarn distributions][example-provided-distribution-project]
        - [Multi frontend projects build][example-multi-frontend-projects]
        - [Full-stack multi projects build][example-full-stack-multi-projects]
    - [Final steps](#final-steps)
      - [Build the frontend](#build-the-frontend)
      - [Use Node/npm/npx/Yarn apart from Gradle](#use-nodenpmnpxyarn-apart-from-gradle)
    - [Recommendations](#recommendations)
      - [Using `*Script` properties](#using-script-properties)
      - [Customizing built-in tasks](#customizing-built-in-tasks)
- [Tasks reference](#tasks-reference)
  - [Task tree](#task-tree)
  - [Install Node.js](#install-nodejs)
  - [Install Yarn](#install-yarn)
  - [Install frontend dependencies](#install-frontend-dependencies)
  - [Clean frontend](#clean-frontend)
  - [Assemble frontend](#assemble-frontend)
  - [Check frontend](#check-frontend)
  - [Publish frontend](#publish-frontend)
  - [Run custom command with `node`](#run-a-custom-command-with-node)
  - [Run custom command with `npx`](#run-a-custom-command-with-npx)
  - [Run custom command with `npm` or `yarn`](#run-a-custom-command-with-npm-or-yarn)
- [Special thanks](#special-thanks)
- [Contributing][contributing]

## Quick start guide

For convenience, configuration blocks in this guide are introduced with both Groovy and Kotlin syntaxes.

### Requirements

The plugin supports:
- [Gradle][gradle] 5.1+
- [JDK][jdk] 8+ 64 bits
- [Node.js][nodejs] 6.2.1+
- [Yarn][yarn] 1.0.0+

The plugin is built and tested on Linux, Mac OS, Windows. See the [contributing notes][contributing] to know the list of
build environments used.

### Installation

Follow instructions provided by the Gradle Plugin Portal [here][gradle-plugin-page].

### Configuration

#### DSL reference

All settings are introduced hereafter. Values of optional properties are the default ones applied by the plugin, unless
otherwise stated. Other values are provided for information. You may also take a look at
[Tasks reference](#tasks-reference) section for further information.

- Groovy syntax:

```groovy
// build.gradle
frontend {
    ////// NODE SETTINGS //////
    // [OPTIONAL] Whether the Node distribution is already provided, and shall not be downloaded.
    // The 'nodeInstallDirectory' property shall be used to point the install directory of the
    // distribution, while other 'node*' properties should not be used for clarity. 
    // If <false>, at least the 'nodeVersion' property must be set.
    nodeDistributionProvided = false

    // [OPTIONAL] Node version, used to build the URL to download the corresponding distribution, if the
    // 'nodeDistributionUrl' property is not set. By default, this property is 'null'
    nodeVersion = '12.16.1'

    // [OPTIONAL] Sets this property to force the download from a custom website. By default, this
    // property is 'null', and the plugin attempts to download the distribution compatible with the
    // current platform from Node's website. The version of the distribution is expected to be the
    // same as the one set in the 'nodeVersion' property, or this may lead to unexpected results.
    nodeDistributionUrl = 'https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip'

    // [OPTIONAL] Install directory where the distribution archive shall be exploded.
    nodeInstallDirectory = file("${projectDir}/node")

    ////// YARN SETTINGS //////
    // [OPTIONAL] Whether Yarn shall be used instead of npm when executing frontend tasks.
    // Consequently, a Yarn distribution will be downloaded and installed by the plugin. If <true>,
    // the 'yarnVersion' property must be set.
    yarnEnabled = false

    // [OPTIONAL] Whether the Yarn distribution is already provided, and shall not be downloaded.
    // The 'yarnInstallDirectory' property shall be used to point the install directory of the
    // distribution, while other 'yarn*' properties should not be used for clarity. 
    // If <false>, at least the 'yarnVersion' property must be set.
    yarnDistributionProvided = false

    // [OPTIONAL] Yarn version, used to build the URL to download the corresponding distribution, if
    // the 'yarnDistributionUrl' property is not set. This property is mandatory when the
    // 'yarnEnabled' property is true.
    yarnVersion = '1.22.4'

    // [OPTIONAL] Sets this property to force the download from a custom website. By default, this
    // property is 'null', and the plugin attempts to download the distribution compatible with the
    // current platform from Yarn's website. The version of the distribution is expected to be the
    // same as the one set in the 'yarnVersion' property, or this may lead to unexpected results.
    yarnDistributionUrl = 'https://github.com/yarnpkg/yarn/releases/download/vX.Y.Z/yarn-vX.Y.Z.tar.gz'

    // [OPTIONAL] Install directory where the distribution archive shall be exploded.
    yarnInstallDirectory = file("${projectDir}/yarn")

    ////// SCRIPT SETTINGS //////
    // Name of npm/Yarn scripts (see 'package.json' file) that shall be executed depending on this
    // plugin task. The values below are passed as arguments of the 'npm' or 'yarn' executables.
    // Under Unix-like O/S, white space characters ' ' in an argument value must be escaped with a
    // backslash character '\'. Under Windows O/S, the whole argument must be enclosed between
    // double-quotes. Example: assembleScript = 'run assemble single\ argument'

    // [OPTIONAL] Use this property to customize the command line used to install frontend
    // dependencies. This property is used by the 'installFrontend' task.
    installScript = 'install'

    // [OPTIONAL] Use this property only if frontend's compiled resources are generated out of the
    // '${project.buildDir}' directory. Default value is <null>. This property is used by the
    // 'cleanFrontend' task. Apart from direct execution, the task is also executed when the Gradle
    // built-in 'clean' task is executed.
    cleanScript = 'run clean'

    // [OPTIONAL] Script called to build frontend's artifacts. Default value is <null>. This
    // property is used by the 'assembleFrontend' task. Apart from direct execution, the task is
    // also executed when the Gradle built-in 'assemble' task is executed.
    assembleScript = 'run assemble'

    // [OPTIONAL] Script called to check the frontend. Default value is <null>. This property is
    // used by the 'checkFrontend' task. Apart from direct execution, the task is also executed
    // when the Gradle built-in 'check' task is executed.
    checkScript = 'run check'

    // [OPTIONAL] Script called to publish the frontend. Default value is <null>. This property is
    // used by the 'publishFrontend' task. Apart from direct execution, the task is also executed
    // when the Gradle built-in 'publish' task is executed.
    publishScript = 'run publish'

    ////// GENERAL SETTINGS //////
    // [OPTIONAL] Location of the directory containing the 'package.json' file. By default, this
    // file is considered to be located in the project's directory, at the same level than this
    // 'build.gradle[.kts]' file. If the 'package.json' file is located in another directory, it is
    // recommended either to set up a Gradle multi-project build, or to set this property with the
    // appropriate directory. This directory being used as the working directory when running JS
    // scripts, consequently, the 'node_modules' directory would be created at this location after
    // the 'installFrontend' task is executed.
    packageJsonDirectory = file("$projectDir")

    // [OPTIONAL] IP address or domain name of a HTTP/HTTPS proxy server to use when downloading
    // distributions. By default, this property is 'null', and the plugin uses direct connections.
    proxyHost = '127.0.0.1'

    // [OPTIONAL] Port of the proxy server. This property is only relevant when the 'proxyHost'
    // property is set.
    proxyPort = 8080

    // [OPTIONAL] Whether the plugin shall log additional messages whatever Gradle's logging level
    // is. Technically speaking, messages logged by the plugin with the INFO level are made
    // visible. This property allows to track the plugin execution without activating Gradle's INFO
    // or DEBUG levels, that may be too much verbose on a global point of view.
    verboseModeEnabled = false
}
```

- Kotlin syntax:

```kotlin
// build.gradle.kts
frontend {
    nodeDistributionProvided.set(false)
    nodeVersion.set("12.16.1")
    nodeDistributionUrl.set("https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip")
    nodeInstallDirectory.set(project.layout.projectDirectory.dir("node"))

    yarnEnabled.set(false)
    yarnDistributionProvided.set(false)
    yarnVersion.set("1.22.4")
    yarnDistributionUrl.set("https://github.com/yarnpkg/yarn/releases/download/vX.Y.Z/yarn-vX.Y.Z.tar.gz")
    yarnInstallDirectory.set(project.layout.projectDirectory.dir("yarn"))

    installScript.set("install")
    cleanScript.set("run clean")
    assembleScript.set("run assemble")
    checkScript.set("run check")
    publishScript.set("run publish")

    packageJsonDirectory.set(project.layout.projectDirectory)
    proxyHost.set("127.0.0.1")
    proxyPort.set(8080)
    verboseModeEnabled.set(false)
}
```

#### Examples

See examples introduced [here][examples].

#### Final steps

##### Build the frontend

Now that the plugin is correctly installed and configured, open a terminal, and execute the following command in the
project's directory:

```sh
gradlew build
```

##### Use Node/npm/npx/Yarn apart from Gradle
 
If you plan to use the downloaded distributions of [Node.js][nodejs]/[npm][npm]/[npx][npx] or [Yarn][yarn] apart from
Gradle, apply the following steps:

- Create a `NODEJS_HOME` environment variable containing the real path set in the `nodeInstallDirectory` property.
- Add the Node/npm executables' directory to the `PATH` environment variable:
  - On Unix-like O/S, add the `$NODEJS_HOME/bin` path.
  - On Windows O/S, add `%NODEJS_HOME%` path.

Optionally, if Yarn is enabled and you don't want to enter Yarn's executable absolute path on a command line:

- Create a `YARN_HOME` environment variable containing the real path set in the `yarnInstallDirectory` property.
- Add the Yarn executable's directory to the `PATH` environment variable:
  - On Unix-like O/S, add the `$YARN_HOME/bin` path.
  - On Windows O/S, add the `%YARN_HOME%\bin` path.

#### Recommendations

##### Using `*Script` properties

Design of the plugin's tasks running a [Node.js][nodejs]/[npm][npm]/[npx][npx]/[Yarn][yarn] command
(e.g. `assembleFrontend` task) rely on the assumption the `package.json` file contains all definitions of the frontend
build actions, and is the single resource defining how to build the frontend, execute unit tests, lint source code, run
a development server, publish artifacts... Our recommendation is to keep these definitions in this file, in the
`scripts` section, and avoid as much as possible using the plugin `*Script` properties to run complex commands. Keep the
frontend build definitions in one place, and let everyone easily figure out where they are located. In an ideal
situation, these properties shall all have a value such as `run <script-name>`, and nothing more. For example:

```groovy
// Instead of:
assembleScript = 'run webpack -- --config webpack.config.js --profile'

// Prefer:
assembleScript = 'run build'
// with a package.json file containing:
// "scripts": {
//   "build": "webpack --config webpack/webpack.prod.js --profile"
// }
```

##### Customizing built-in tasks

If you need to customize the plugin built-in tasks (e.g. declare additional I/O or dependencies), it is very important
to conform to the [Configuration avoidance API][gradle-configuration-avoidance-api], use references of task providers
instead of references of tasks, and continue taking advantage of the lazy configuration behavior the plugin already
implements. The examples below introduce the implementation expected with simple cases:

```groovy
// Configuring a predefined task.
// FORMER SYNTAX: task 'installFrontend' is immediately created and configured, as well as task
// 'otherTask', even if both tasks are not executed.
installFrontend {
    dependsOn 'otherTask'
}
// MODERN SYNTAX: task 'installFrontend' is created and configured only when Gradle is about to execute it.
// Consequently, task 'otherTask' is also created and configured later.
tasks.named('installFrontend') {
    dependsOn 'otherTask'
}

// Defining a new task
// LEGACY SYNTAX: task 'eagerTask' is immediately created and configured, as well as task
// 'installFrontend', even if both tasks are not executed.
task eagerTask {
    dependsOn 'installFrontend' 
}
// MODERN SYNTAX: task 'eagerTask' is created and configured only when Gradle is about to execute it.
// Consequently, task 'installFrontend' is also created and configured later.
tasks.register('eagerTask') {
    dependsOn 'installFrontend'
}
```

If your application uses the former syntax, you may find further instructions to migrate in this Gradle's
[guide][gradle-migration-guide].

## Tasks reference

The plugin registers multiple tasks, that may have dependencies with each other, and also with:
- Gradle lifecycle tasks defined in the [Gradle Base plugin][gradle-base-plugin]: `clean`, `assemble`, `check`.
- Tasks defined in the Gradle Publishing plugin: `publish`.

### Task tree

![Task tree][task-tree]

### Install Node.js

The `installNode` task downloads a [Node.js][nodejs] distribution and verifies its integrity. If the
`nodeDistributionUrl` property is ommitted, the URL is guessed using the `nodeVersion` property. Checking the
distribution integrity consists of downloading a file providing the distribution shasum. This file is expected to be in
the same remote web directory than the distribution. For example, if the distribution is located at URL
`https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip`, the plugin attempts to download the shasum file located at
URL `https://nodejs.org/dist/vX.Y.Z/SHASUMS256.txt`. Optionally, defining the `proxyHost` property and the `proxyPort`
property allow to use a proxy server when downloading the distribution and the shasum. Set the location where the
distribution shall be installed with the `nodeInstallDirectory` property, which by default is the `${projectDir}/node`
directory.

If a [Node.js][nodejs] distribution is already installed in the system, and shall be used instead of a downloaded
distribution, set the `nodeDistributionProvided` property to `true` and the location of the distribution with the
`nodeInstallDirectory` property. The `nodeVersion` property and the `nodeDistributionUrl` property are useless and shall
not be set for clarity. Consequently, the `installNode` task is automatically _SKIPPED_ during a Gradle build.

The task takes advantage of [Gradle incremental build][gradle-incremental-build], and is not executed again unless one
of its inputs/outputs changed. The task is _UP-TO-DATE_ during a Gradle build, and skipped.

> This task should not be executed directly. Gradle executes it if the build requires it.

### Install Yarn

The `installYarn` task downloads a [Yarn][yarn] distribution, if `yarnEnabled` property is `true`. If the
`yarnDistributionUrl` property is ommitted, the URL is guessed using the `yarnVersion` property. Optionally, defining
the `proxyHost` property and the `proxyPort` property allow to use a proxy server when downloading the distribution. Set
the location where the distribution shall be installed with the `yarnInstallDirectory` property, which by default is the
`${projectDir}/yarn` directory.

If a [Yarn][yarn] distribution is already installed in the system, and shall be used instead of a downloaded
distribution, set the `yarnDistributionProvided` property to `true` and the location of the distribution with the
`yarnInstallDirectory` property. The `yarnEnabled` property still must be `true`, the `yarnVersion` property and the
`yarnDistributionUrl` property are useless and shall not be set for clarity. Consequently, the `installYarn` task is
automatically _SKIPPED_ during a Gradle build.

The task takes advantage of [Gradle incremental build][gradle-incremental-build], and is not executed again unless one
of its inputs/outputs changed. The task is _UP-TO-DATE_ during a Gradle build, and skipped.

> This task should not be executed directly. Gradle executes it if the build requires it.

### Install frontend dependencies

Depending on the value of the `yarnEnabled` property, the `installFrontend` task issues either a `npm install` command
or a `yarn install` command, by default. If a `package.json` file is found in the directory pointed by the
`packageJsonDirectory` property, the command shall install dependencies and tools for frontend development. Optionally,
this command may be customized (e.g. to run a `npm ci` command instead of a `npm install` command). To do so, the
`installScript` property must be set to the corresponding [npm][npm]/[Yarn][yarn] command. This task depends on the
`installNode` task, and optionally on the `installYarn` task if the `yarnEnabled` property is `true`.

> This task may be executed directly, e.g. after modifying one of the Node.js/Yarn version and/or to update frontend
> dependencies. Otherwise, Gradle executes it if the build requires it.

### Clean frontend

The `cleanFrontend` task does nothing by default, considering frontend generated resources (pre-processed Typescript
files, SCSS stylesheets...) are written in the `${project.buildDir}` directory. If it is not the case, this task may be
useful to clean the relevant directory. To do so, a clean script must be defined in the `package.json` file,
and the `cleanScript` property must be set to the corresponding npm/Yarn command. This task depends on the
`installFrontend` task, and is skipped if the `cleanScript` property is not set.

### Assemble frontend

The `assembleFrontend` task shall be used to integrate a frontend's build script into Gradle builds. The build script
must be defined in the `package.json` file, and the `assembleScript` property must be set to the corresponding
npm/Yarn command. This task depends on the `installFrontend` task, and is skipped if the `assembleScript` property is
not set.

### Check frontend

The `checkFrontend` task may be used to integrate a frontend check script into a Gradle build. The check script must be
defined in the project's `package.json` file, and the `checkscript` property must be set with the corresponding
npm/Yarn command. A typical check script defined in the project's `package.json` file may lint frontend source files,
execute tests, and perform additional analysis tasks. This task depends on the `installFrontend` task, and is skipped if
the `checkScript` property is not set.

### Publish frontend

The `publishFrontend` task may be used to integrate a frontend publish script into a Gradle build. The publish script
must be defined in the project's `package.json` file, and the `publishScript` property must be set with the
corresponding npm/Yarn command. This task depends on the task `assembleFrontend`, and is skipped if either the
`assembleScript` property or the `publishScript` property is not set.

### Run a custom command with `node`

The plugin provides the task type `org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode` that allows creating a
custom task to run a JS script. The `script` property must be set with the corresponding Node command. Then, choose
whether [Node.js][nodejs] only is required, or if additional dependencies located in the `package.json` file should be
installed: make the task either depends on `installNode` task or on `installFrontend` task.

The code below shows the configuration required to run a JS `my-custom-script.js` with Node:

- Groovy syntax:

```groovy
// build.gradle
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode
tasks.register('myCustomScript', RunNode) {
    // dependsOn tasks.named('installNode')
    // dependsOn tasks.named('installFrontend')
    script = 'my-custom-script.js'
}
```

- Kotlin syntax:

```kotlin
// build.gradle.kts
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode
tasks.register<RunNode>("myCustomScript") {
    // dependsOn(tasks.named("installNode"))
    // dependsOn(tasks.named("installFrontend"))
    script.set("my-custom-script.js")
}
```

### Run a custom command with `npx`

> Requires Node.js 8.2.0+ on Unix-like O/S, Node.js 8.5.0+ on Windows O/S

The plugin provides the task type `org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx` that allows creating a
custom task to run a npx command. The `script` property must be set with the corresponding [npx][npx] command. Custom
tasks will fail if the `yarnEnabled` property is `true`, to prevent unpredictable behaviors with mixed installation of
dependencies.

The code below shows the configuration required to display npx version:

- Groovy syntax:

```groovy
// build.gradle
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx
tasks.register('npxVersion', RunNpx) {
    dependsOn tasks.named('installNode')
    script = '--version'
}
```

- Kotlin syntax:

```kotlin
// build.gradle.kts
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx
tasks.register<RunNpx>("npxVersion") {
    dependsOn(tasks.named("installNode"))
    script.set("--version")
}
```

### Run a custom command with `npm` or `yarn`

The plugin provides the task type `org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn` that allows creating
a custom task to run any frontend script. The `script` property must be set with the corresponding
[npm][npm]/[Yarn][yarn] command.

The code below shows the configuration required to run frontend's end-to-end tests in a custom task:

- Groovy syntax:

```groovy
// build.gradle
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn
tasks.register('e2e', RunNpmYarn) {
    dependsOn tasks.named('installFrontend')
    script = 'run e2e'
}
```

- Kotlin syntax:

```kotlin
// build.gradle.kts
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn
tasks.register<RunNpmYarn>("e2e") {
    dependsOn(tasks.named("installFrontend"))
    script.set("run e2e")
}
```

## Special thanks

The plugin is developed using [Intellij IDEA][intellij-idea], special thanks to [JetBrains][jetbrains] for this amazing
IDE, and their support to this project.

![Jetbrains logo][jetbrains-logo]
![IntelliJ IDEA logo][intellij-idea-logo]

With their feedback, plugin improvement is possible. Special thanks to:

@andreaschiona, @byxor, @ChFlick, @ckosloski, @davidkron, @mike-howell, @napstr, @nuth, @rolaca11, @TapaiBalazs

[clean-coder]: <http://cleancoder.com/> (Clean coder)
[contributing]: <CONTRIBUTING.md> (Contributing to this project)
[example-full-stack-multi-projects]: <examples/full-stack-multi-projects> (Configure a full-stack multi projects build)
[example-multi-frontend-projects]: <examples/multi-frontend-projects> (Configure a multi frontend projects build)
[example-provided-distribution-project]: <examples/provided-distribution-project> (Configure a standalone frontend build with preinstalled Node.js/Yarn distributions)
[example-standalone-frontend-project]: <examples/standalone-frontend-project> (Configure a standalone frontend build)
[examples]: <examples> (Examples)
[frontend-maven-plugin]: <https://github.com/eirslett/frontend-maven-plugin> (Frontend Maven plugin)
[gradle]: <https://gradle.org/> (Gradle)
[gradle-base-plugin]: <https://docs.gradle.org/current/userguide/base_plugin.html> (Gradle Base plugin)
[gradle-build-script-block]: <https://docs.gradle.org/current/userguide/plugins.html#sec:applying_plugins_buildscript> (Gradle build script block)
[gradle-configuration-avoidance-api]: <https://docs.gradle.org/current/userguide/task_configuration_avoidance.html#sec:old_vs_new_configuration_api_overview> (Configuration avoidance API overview)
[gradle-dsl]: <https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block> (Gradle DSL)
[gradle-incremental-build]: <https://guides.gradle.org/performance/#incremental_build> (Gradle incremental build)
[gradle-lazy-configuration]: <https://docs.gradle.org/current/userguide/lazy_configuration.html> (Lazy configuration)
[gradle-icon]: <resources/gradle-icon.png> (Gradle)
[gradle-migration-guide]: <https://docs.gradle.org/current/userguide/task_configuration_avoidance.html#sec:task_configuration_avoidance_migration_guidelines> (Migration guide)
[gradle-plugin-page]: <https://plugins.gradle.org/plugin/org.siouan.frontend> (Frontend Gradle plugin's official page at the Gradle Plugin Portal)
[gradle-task-configuration-avoidance]: <https://docs.gradle.org/current/userguide/task_configuration_avoidance.html> (Task configuration avoidance)
[intellij-idea]: <https://www.jetbrains.com/idea/> (IntelliJ IDEA)
[intellij-idea-logo]: <resources/intellij-idea-128x128.png> (IntelliJ IDEA)
[jdk]: <https://docs.oracle.com/en/java/javase/> (Java Development Kit)
[jetbrains]: <https://www.jetbrains.com/> (JetBrains)
[jetbrains-logo]: <resources/jetbrains-128x128.png> (JetBrains)
[nodejs]: <https://nodejs.org/> (Node.js)
[nodejs-icon]: <resources/nodejs-icon.png> (Node.js)
[npm]: <https://www.npmjs.com/> (npm)
[npm-icon]: <resources/npm-icon.png> (NPM)
[npx]: <https://github.com/npm/npx> (npx)
[release-notes]: <https://github.com/siouan/frontend-gradle-plugin/releases> (Release notes)
[task-tree]: <resources/task-tree.png>
[yarn]: <https://yarnpkg.com/> (Yarn)
[yarn-icon]: <resources/yarn-icon.png> (Yarn)
