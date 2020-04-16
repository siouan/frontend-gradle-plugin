# Frontend Gradle plugin

[![Latest release 2.0.0](https://img.shields.io/badge/Latest%20release-2.0.0-blue.svg)](https://github.com/Siouan/frontend-gradle-plugin/releases/tag/v2.0.0)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

[![Build status](https://travis-ci.com/Siouan/frontend-gradle-plugin.svg?branch=2.0)](https://travis-ci.com/Siouan/frontend-gradle-plugin)
[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin)
[![Code coverage](https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=coverage)](https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin)
[![Reliability](https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin)

This plugin allows to integrate a [Node.js][nodejs]/[NPM][npm]/[Yarn][yarn] build into Gradle. It is inspired by the
philosophy of the [frontend-maven-plugin][frontend-maven-plugin]. See the [quick start guide](#quick-start-guide) below
to install/configure the plugin, and build your frontend application.

#### Features

- Download and install a [Node.js][nodejs] distribution.
- Download and install a [Yarn][yarn] distribution.
- Install frontend dependencies with a configurable NPM/Yarn command (e.g. `npm ci`).
- Trigger automatically frontend build, check, clean, publish scripts in a `package.json` file from Gradle lifecycle
tasks.
- Create custom tasks to run a command with [Node.js][nodejs]/[NPX][npx]/[NPM][npm]/[Yarn][yarn].
- Use a provided distribution of [Node.js][nodejs] or [Yarn][yarn].

## Summary

- [Quick start guide](#quick-start-guide)
  - [Requirements](#requirements)
  - [Installation](#installation)
    - [Using Gradle DSL](#using-gradle-dsl)
    - [Using Gradle build script block](#using-gradle-build-script-block)
  - [Configuration](#configuration)
    - [DSL reference](#dsl-reference)
    - [Examples][examples]
        - [Standalone frontend project build][example-standalone-frontend-project]
        - [Standalone frontend project build with preinstalled Node.js/Yarn distributions][example-provided-distribution-project]
        - [Multi frontend projects build][example-multi-frontend-projects]
        - [Full-stack multi projects build][example-full-stack-multi-projects]
  - [Final steps](#final-steps)
    - [Build the frontend](#build-the-frontend)
    - [Use Node/NPX/NPM/Yarn apart from Gradle](#use-nodenpxnpmyarn-apart-from-gradle)
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
- [Usage guidelines][guidelines]
  - [Packaging a frontend application in a Java artifact][single-java-artifact-packaging]
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

2 options are available.

#### Using [Gradle DSL][gradle-dsl]

This is the modern and recommended approach.

- Groovy syntax:

```groovy
// build.gradle
plugins {
    id 'org.siouan.frontend' version '2.0.0'
}
```

- Kotlin syntax:

```kotlin
// build.gradle.kts
plugins {
    id("org.siouan.frontend") version "2.0.0"
}
```

#### Using [Gradle build script block][gradle-build-script-block]

This approach is the legacy way to resolve and apply plugins.

- Groovy syntax:

```groovy
// build.gradle
buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        classpath 'org.siouan:frontend-gradle-plugin:2.0.0'
    }
}

apply plugin: 'org.siouan.frontend'
```

- Kotlin syntax:

```kotlin
// build.gradle.kts
buildscript {
    repositories {
        url = uri("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.siouan:frontend-gradle-plugin:2.0.0")
    }
}

apply(plugin = "org.siouan.frontend")
```

### Configuration

#### DSL reference

All settings are introduced hereafter. Values of optional properties are the default ones applied by the plugin. Other
values are provided for information. You may also take a look at [Tasks reference](#tasks-reference) section for
additional information.

- Groovy syntax:

```groovy
// build.gradle
frontend {
    ////// NODE SETTINGS //////
    // [OPTIONAL] Whether the Node distribution is already provided, and shall not be downloaded.
    // The 'nodeInstallDirectory' shall be used to point the install directory of the distribution.
    // If <false>, the 'nodeVersion' property - at least, must be set.
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
    // [OPTIONAL] Whether Yarn shall be used instead of NPM when executing frontend tasks.
    // Consequently, a Yarn distribution will be downloaded and installed by the plugin. If <true>,
    // the 'yarnVersion' version property must be set.
    yarnEnabled = false

    // [OPTIONAL] Whether the Yarn distribution is already provided, and shall not be downloaded.
    // The 'yarnInstallDirectory' shall be used to point the install directory of the distribution.
    // If <false>, the 'yarnVersion' property - at least, must be set.
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
    // Name of NPM/Yarn scripts (see 'package.json' file) that shall be executed depending on this
    // plugin task. The values below are passed as arguments of the 'npm' or 'yarn' executables.
    // Under Linux-like O/S, white space characters ' ' in an argument value must be escaped with a
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

    // [OPTIONAL] Default level used by the plugin to log messages in Gradle. This property allows
    // to set a specific level for this plugin only. It does not take precedence over Gradle
    // logging level at execution, i.e. it must be higher or equal than the logging level set on
    // the command line so as messages are visible. The plugin also logs some messages with a
    // specific level, independently from this setting (e.g. debugging data).
    loggingLevel = LogLevel.LIFECYCLE
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
    loggingLevel.set(LogLevel.LIFECYCLE)
}
```

#### Examples

See examples introduced [here][examples].

### Final steps

#### Build the frontend

Now that the plugin is correctly installed and configured, open a terminal, and execute the following command in the
project's directory:

```sh
gradlew build
```

If the frontend application shall be packaged in a Java artifact, take a look at [this guide](#how-to-assemble-a-frontend-and-a-java-backend-into-a-single-artifact)
to configure frontend and backend applications assembling.

#### Use Node/NPX/NPM/Yarn apart from Gradle
 
If you plan to use the downloaded distributions of [Node.js][nodejs]/[NPX][npx]/[NPM][npm] or [Yarn][yarn] apart from
Gradle, apply the following steps:

- Create a `NODEJS_HOME` environment variable containing the real path set in the `nodeInstallDirectory` property.
- Add the Node/NPM executables' directory to the `PATH` environment variable:
  - On Unix-like O/S, add the `$NODEJS_HOME/bin` path.
  - On Windows O/S, add `%NODEJS_HOME%` path.

Optionally, if Yarn is enabled and you don't want to enter Yarn's executable absolute path on a command line:

- Create a `YARN_HOME` environment variable containing the real path set in the `yarnInstallDirectory` property.
- Add the Yarn executable's directory to the `PATH` environment variable:
  - On Unix-like O/S, add the `$YARN_HOME/bin` path.
  - On Windows O/S, add the `%YARN_HOME%\bin` path.

## Tasks reference

The plugin registers multiple tasks, that may have dependencies with each other, and also with:
- Gradle lifecycle tasks defined in the [Gradle Base plugin][gradle-base-plugin]: `clean`, `assemble`, `check`.
- Tasks defined in the Gradle Publishing plugin: `publish`.

### Task tree

![Task tree][task-tree]

### Install Node.js

The `installNode` task downloads a [Node.js][nodejs] distribution and verifies its integrity. If the
`nodeDistributionUrl` property is ommitted, the URL is guessed using the `nodeVersion` property. Distribution integrity
is checked by downloading a file providing the distribution shasum. This file is expected to be in the same remote web
directory than the distribution. For example, if the distribution is located at URL
`https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip`, the plugin attempts to download the shasum file located at
URL `https://nodejs.org/dist/vX.Y.Z/SHASUMS256.txt`. Use the property `nodeInstallDirectory` to set the location where
the distribution shall be installed, which by default is the `${projectDir}/node` directory.

If a Node distribution is already installed in the system, and shall be used instead of a downloaded distribution, sets
the `nodeDistributionProvided` property to `true` and the location of the distribution with the `nodeInstallDirectory`
property. In this configuration, the `nodeVersion` property and the `nodeDistributionUrl` property are useless and shall
not be set for clarity. Consequently, the `installNode` task is automatically _SKIPPED_ during a Gradle build.

The task takes advantage of
[Gradle incremental build][gradle-incremental-build], and is not executed again unless at least one of the events below
occurs:

- The plugins change in the project.
- At least one of the properties `nodeVersion`, `nodeDistributionUrl`, `nodeInstallDirectory` is modified.
- The content of the directory pointed by the `nodeInstallDirectory` is modified.

In other cases, the task will be marked as _UP-TO-DATE_ during a Gradle build, and skipped.

This task should not be executed directly. It is called automatically by Gradle, if the `installFrontend` task is
executed.

### Install Yarn

The `installYarn` task downloads a [Yarn][yarn] distribution, if `yarnEnabled` property is `true`. If the
`yarnInstallDirectory` property is ommitted, the URL is guessed using the `yarnVersion` property. Use the property
`yarnInstallDirectory` to set the location where the distribution shall be installed, which, by default is the
`${projectDir}/yarn` directory.

If a Yarn distribution is already installed in the system, and shall be used instead of a downloaded distribution, sets
the `yarnDistributionProvided` property to `true` and the location of the distribution with the `yarnInstallDirectory`
property. In this configuration, the `yarnEnabled` property still must be `true`, the `yarnVersion` property and the
`yarnDistributionUrl` property are useless and shall not be set for clarity. Consequently, the `installYarn` task is
automatically _SKIPPED_ during a Gradle build.

The task takes advantage of [Gradle incremental build][gradle-incremental-build], and is not executed again unless at
least one of the events below occurs:

- The plugins change in the project.
- At least one of the properties `yarnEnabled`, `yarnVersion`, `yarnDistributionUrl`, `yarnInstallDirectory` is
modified.
- The content of the directory pointed by the `yarnInstallDirectory` is modified.

In other cases, the task will be marked as _UP-TO-DATE_ during a Gradle build, and skipped.

This task should not be executed directly. It is called automatically by Gradle, if the `installFrontend` task is
executed.

### Install frontend dependencies

Depending on the value of the `yarnEnabled` property, the `installFrontend` task issues either a `npm install` command
or a `yarn install` command, by default. If a `package.json` file is found in the directory pointed by the
`packageJsonDirectory` property, the command shall install dependencies and tools for frontend development. Optionally,
this command may be customized (e.g. to run a `npm ci` command instead of a `npm install` command). To do so, the
`installScript` property must be set to the corresponding [NPM][npm]/[Yarn][yarn] command. This task depends on the
`installNode` task, and optionally on the `installYarn` task if the `yarnEnabled` property is `true`.

This task may be executed directly, e.g. if one of the Node/Yarn version is modified, and a distribution must be
downloaded again. Otherwise, this task is called automatically by Gradle, if one of these tasks is executed:
`cleanFrontend`, `assembleFrontend`, `checkFrontend`, `publishFrontend`.

### Clean frontend

The `cleanFrontend` task does nothing by default, considering frontend generated resources (pre-processed Typescript
files, SCSS stylesheets...) are written in the `${project.buildDir}` directory. If it is not the case, this task may be
useful to clean the relevant directory. To do so, a clean script must be defined in the `package.json` file,
and the `cleanScript` property must be set to the corresponding NPM/Yarn command. This task depends on the
`installFrontend` task, and is skipped if the `cleanScript` property is not set.

### Assemble frontend

The `assembleFrontend` task shall be used to integrate a frontend's build script into Gradle builds. The build script
must be defined in the `package.json` file, and the `assembleScript` property must be set to the corresponding
NPM/Yarn command. This task depends on the `installFrontend` task, and is skipped if the `assembleScript` property is
not set.

### Check frontend

The `checkFrontend` task may be used to integrate a frontend check script into a Gradle build. The check script must be
defined in the project's `package.json` file, and the `checkscript` property must be set with the corresponding
NPM/Yarn command. A typical check script defined in the project's `package.json` file may lint frontend source files,
execute tests, and perform additional analysis tasks. This task depends on the `installFrontend` task, and is skipped if
the `checkScript` property is not set.

### Publish frontend

The `publishFrontend` task may be used to integrate a frontend publish script into a Gradle build. The publish script
must be defined in the project's `package.json` file, and the `publishScript` property must be set with the
corresponding NPM/Yarn command. This task depends on the task `assembleFrontend`, and is skipped if either the
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

The plugin provides the task type `org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx` that allows creating a
custom task to run a NPX command. The `script` property must be set with the corresponding [NPX][npx] command. Custom
tasks will fail if the `yarnEnabled` property is `true`, to prevent unpredictable behaviours with mixed installation of
dependencies.

The code below shows the configuration required to display NPX version:

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
[NPM][npm]/[Yarn][yarn] command.

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

[contributing]: <CONTRIBUTING.md> (Contributing to this project)
[example-full-stack-multi-projects]: <examples/full-stack-multi-projects/README.md> (Configure a full-stack multi projects build)
[example-multi-frontend-projects]: <examples/multi-frontend-projects/README.md> (Configure a multi frontend projects build)
[example-provided-distribution-project]: <examples/provided-distribution-project/README.md> (Configure a standalone frontend build with preinstalled Node.js/Yarn distributions)
[example-standalone-frontend-project]: <examples/standalone-frontend-project/README.md> (Configure a standalone frontend build)
[examples]: <examples> (Examples)
[frontend-maven-plugin]: <https://github.com/eirslett/frontend-maven-plugin> (Frontend Maven plugin)
[gradle]: <https://gradle.org/> (Gradle)
[gradle-base-plugin]: <https://docs.gradle.org/current/userguide/base_plugin.html> (Gradle Base plugin)
[gradle-build-script-block]: <https://docs.gradle.org/current/userguide/plugins.html#sec:applying_plugins_buildscript> (Gradle build script block)
[gradle-dsl]: <https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block> (Gradle DSL)
[gradle-incremental-build]: <https://guides.gradle.org/performance/#incremental_build> (Gradle incremental build)
[guidelines]: <resources/GUIDELINES.md> (Usage guidelines)
[intellij-idea]: <https://www.jetbrains.com/idea/> (IntelliJ IDEA)
[intellij-idea-logo]: <resources/intellij-idea-128x128.png> (IntelliJ IDEA)
[jdk]: <https://docs.oracle.com/en/java/javase/> (Java Development Kit)
[jetbrains]: <https://www.jetbrains.com/> (JetBrains)
[jetbrains-logo]: <resources/jetbrains-128x128.png> (JetBrains)
[nodejs]: <https://nodejs.org/> (Node.js)
[npm]: <https://www.npmjs.com/> (NPM)
[npx]: <https://github.com/npm/npx> (NPX)
[release-notes]: <https://github.com/siouan/frontend-gradle-plugin/releases> (Release notes)
[single-java-artifact-packaging]: <resources/GUIDELINES.md#packaging-a-frontend-application-in-a-java-artifact> (Packaging a frontend application in a Java artifact)
[task-tree]: <resources/task-tree.png>
[yarn]: <https://yarnpkg.com/> (Yarn)
