# Frontend Gradle plugin

[![Latest release 1.1.3](https://img.shields.io/badge/Latest%20release-1.1.3-blue.svg)](https://github.com/Siouan/frontend-gradle-plugin/releases/tag/v1.1.3)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

[![Build status](https://travis-ci.org/Siouan/frontend-gradle-plugin.svg?branch=1.1)](https://travis-ci.org/Siouan/frontend-gradle-plugin)
[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin)
[![Code coverage](https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=coverage)](https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin)
[![Reliability](https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin)

This plugin allows to integrate a frontend NPM/Yarn build into Gradle. It is inspired by the
[frontend-maven-plugin][frontend-maven-plugin]. See the [quick start guide](#quick-start-guide) below to
install/configure the plugin, and build your frontend application.

## Summary

- [Quick start guide](#quick-start-guide)
  - [Requirements](#requirements)
  - [Installation](#installation)
    - [Using Gradle DSL](#using-gradle-dsl)
    - [Using Gradle build script block](#using-gradle-build-script-block)
  - [Configuration](#configuration)
    - [DSL reference](#dsl-reference)
    - [Typical configuration with NPM](#typical-configuration-with-npm)
    - [Typical configuration with Yarn](#typical-configuration-with-yarn)
  - [Final steps](#final-steps)
    - [Build the frontend](#build-the-frontend)
    - [Use Node/NPM/Yarn apart from Gradle](#use-nodenpmyarn-apart-from-gradle)
- [Tasks reference](#tasks-reference)
  - [Task tree](#task-tree)
  - [Install Node](#install-node)
  - [Install Yarn](#install-yarn)
  - [Install frontend dependencies](#install-frontend-dependencies)
  - [Clean frontend](#clean-frontend)
  - [Assemble frontend](#assemble-frontend)
  - [Check frontend](#check-frontend)
  - [Run custom NPM/Yarn script](#run-custom-npmyarn-script)
- [Usage guidelines](#usage-guidelines)
  - [How to assemble a frontend and a Java backend in a single artifact?](#how-to-assemble-a-frontend-and-a-java-backend-in-a-single-artifact)
  - [What kind of script should I attach to the `checkFrontend` task?](#what-kind-of-script-should-i-attach-to-the-checkfrontend-task)
- [Contributing][contributing]

## Quick start guide

### Requirements

The plugin supports:
- [Gradle][gradle] 5.1+
- [JDK][jdk] 8+ 64 bits
- [Node][node] 6.2.1+
- [Yarn][yarn] 1.0.0+

The plugin is built and tested on Linux, Mac OS, Windows. For a full list of build environments used, see the
[contributing notes][contributing].

### Installation

2 options are available.

#### Using [Gradle DSL][gradle-dsl]

This is the modern and recommended approach.

```gradle
// build.gradle
plugins {
    id 'org.siouan.frontend' version '1.1.3'
}
```

#### Using [Gradle build script block][gradle-build-script-block]

This approach is the legacy way to resolve and apply plugins.

```gradle
// build.gradle
buildscript {
    repositories {
        url 'https://plugins.gradle.org/m2/'
    }
    dependencies {
        classpath 'org.siouan:frontend-gradle-plugin:1.1.3'
    }
}

apply plugin: 'org.siouan.frontend'
```

### Configuration

#### DSL reference

All settings are introduced hereafter, with default value for each property.

```gradle
// build.gradle
frontend {
    // NODE SETTINGS
    // Node version, used to build the URL to download the corresponding distribution, if the 'nodeDistributionUrl'
    // property is not set.
    nodeVersion = '10.15.3'

    // [Optional] Sets this property to force the download from a custom website. By default, this property is 'null',
    // and the plugin attempts to download the distribution compatible with the current platform from the Node website.
    // The version of the distribution is expected to be the same as the one set in the 'nodeVersion' property, or this
    // may lead to unexpected results.
    nodeDistributionUrl = 'https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip'

    // [Optional] Install directory where the distribution archive shall be exploded.
    nodeInstallDirectory = "${projectDir}/node"

    // YARN SETTINGS
    // Whether Yarn shall be used instead of NPM when executing frontend tasks. Consequently, a Yarn distribution will
    // be downloaded and installed by the plugin.
    yarnEnabled = false

    // [Optional] Yarn version, used to build the URL to download the corresponding distribution, if the
    // 'yarnDistributionUrl' property is not set. This property is mandatory when the 'yarnEnabled' property is true.
    yarnVersion = '1.15.2'

    // [Optional] Sets this property to force the download from a custom website. By default, this property is 'null',
    // and the plugin attempts to download the distribution compatible with the current platform from the Yarn website.
    // The version of the distribution is expected to be the same as the one set in the 'yarnVersion' property, or this
    // may lead to unexpected results.
    yarnDistributionUrl = 'https://github.com/yarnpkg/yarn/releases/download/vX.Y.Z/yarn-vX.Y.Z.tar.gz'

    // [Optional] Install directory where the distribution archive shall be exploded.
    yarnInstallDirectory = "${projectDir}/yarn"

    // OTHER SETTINGS
    // Name of the NPM/Yarn scripts (see 'package.json' file) that shall be executed depending on the Gradle lifecycle
    // task. The values below are passed as argument of the 'npm' or 'yarn' executables.

    // [Optional] Use this property only if frontend's compiled resources are generated out of the '${project.buildDir}'
    // directory. Default value is <null>. This property is directly used by the 'cleanFrontend' task. The task is
    // run when the Gradle built-in 'clean' task is run.
    cleanScript = 'run clean'

    // [Optional] Script called to build frontend's artifacts. Default value is <null>. This property is directly used
    // by the 'assembleFrontend' task. The task is run when the Gradle built-in 'assemble' task is run.
    assembleScript = 'run assemble'

    // [Optional] Script called to check the frontend. Default value is <null>. This property is directly used by the
    // 'checkFrontend' task. The task is run when the Gradle built-in 'check' task is run.
    checkScript = 'run check'
}
```

#### Typical configuration with NPM

```gradle
// build.gradle
frontend {
    nodeVersion = '<X.Y.Z>'
    cleanScript = 'run clean'
    assembleScript = 'run assemble'
    checkScript = 'run check'
}
```

#### Typical configuration with Yarn

```gradle
// build.gradle
frontend {
    nodeVersion = '<X.Y.Z>'
    yarnEnabled = true
    yarnVersion = '<X.Y.Z>'
    cleanScript = 'run clean'
    assembleScript = 'run assemble'
    checkScript = 'run check'
}
```

### Final steps

#### Build the frontend

Now that the plugin is correctly installed and configured, open a terminal, and execute the following command in the
project's directory:

```sh
gradle build
```

If the frontend application is part of a full-stack Java artifact, take a look at
[this guide](#how-to-assemble-a-frontend-and-a-java-backend-in-a-single-artifact) to assemble the frontend and the
backend together.

#### Use Node/NPM/Yarn apart from Gradle
 
If Node/NPM/Yarn may be used apart from Gradle, it is mandatory to apply the following steps:

- Create a `NODEJS_HOME` environment variable containing the real path set in the `nodeInstallDirectory` property.
- Add the `$NODEJS_HOME` (Unix-like OS) or `%NODEJS_HOME%` (Windows OS) path to the `PATH` environment variable.

If Yarn is enabled, apply also the steps below:

- Create a `YARN_HOME` environment variable containing the real path set in the `yarnInstallDirectory` property.
- Add the `$YARN_HOME/bin` (Unix-like OS) or `%YARN_HOME%\bin` (Windows OS) path to the `PATH`
environment variable.

## Tasks reference

The plugin registers multiple tasks, some having dependencies with other, and also with Gradle lifecycle tasks defined
in the [Gradle base plugin][gradle-base-plugin].

### Task tree

![Task tree][task-tree]

### Install Node

The `installNode` task downloads a Node distribution. If the `distributionUrl` property is ommitted, the URL is
guessed using the `version` property. Use the property `nodeInstallDirectory` to set the directory where the
distribution shall be installed, which, by default is the `${projectDir}/node` directory.

This task should not be executed directly. It will be called automatically by Gradle, if another task depends on it.
 
### Install Yarn

The `installYarn` task downloads a Yarn distribution, if `yarnEnabled` property is `true`. If the `distributionUrl`
property is ommitted, the URL is guessed using the `version` property. Use the property `yarnInstallDirectory` to set
the directory where the distribution shall be installed, which, by default is the `${projectDir}/yarn` directory.

This task should not be executed directly. It will be called automatically by Gradle, if another task depends on it.
 
### Install frontend dependencies

Depending on the value of the `yarnEnabled` property, the `installFrontend` task issues either a `npm install` command
or a `yarn install` command. If a `package.json` file is found in the project's directory, the command shall install
dependencies and tools for frontend development.

This task may be executed directly, especially if the Node distribution and/or the Yarn distribution must be downloaded
again.

### Clean frontend

The `cleanFrontend` task does nothing by default, considering frontend generated resources (pre-processed Typescript
files, SCSS stylesheets...) are written in the `${project.buildDir}` directory. If it is not the case, this task may be
useful to clean the relevant directory. To do so, a clean script must be defined in the project's `package.json` file,
and the `cleanScript` property must be set to the corresponding NPM/Yarn command.

### Assemble frontend

The `assembleFrontend` task shall be used to integrate a frontend's build script into Gradle builds. The build script
must be defined in the project's `package.json` file, and the `assembleScript` property must be set to the corresponding
NPM/Yarn command.

### Check frontend

The `checkFrontend` task shall be used to integrate a frontend's check script into Gradle builds. The check script must
be defined in the project's `package.json` file, and the `checkscript` property must be set with the corresponding
NPM/Yarn command. A typical check script defined in the project's `package.json` file may lint frontend source files,
execute tests, and perform additional analysis tasks.

### Run custom NPM/Yarn script

The `runScriptFrontend` task is provided as a task type, to create custom tasks. The `script` property must be set with
the corresponding NPM/Yarn command. For instance, the code below added in the `build.gradle` file allows to run
frontend's end-to-end tests in a custom task:

```gradle
tasks.register('e2e', org.siouan.frontendgradleplugin.tasks.RunScriptTask) {
    dependsOn tasks.named('installFrontend')
    script = 'run e2e'
}
```

## Usage guidelines

### How to assemble a frontend and a Java backend in a single artifact?

If you plan to serve your frontend with a Java backend (e.g. a [Spring Boot][spring-boot] application), you will
probably use other Gradle plugins, such as the [Gradle Java plugin][gradle-java-plugin], the
[Gradle Spring Boot plugin][gradle-spring-boot-plugin], or other ones of your choice.

In this configuration, you may package your full-stack application as a JAR/WAR artifact. To do so, the frontend must be
assembled before the backend, and generally provided in a special directory for the backend packaging task (e.g.
`jar`/`war`/`bootJar`/`bootWar`... tasks). 

Assembling the frontend before the backend shall not be difficult to setup in Gradle. Below is the task tree of the
`assemble` task when this plugin is used with the [Gradle Java plugin][gradle-java-plugin] or a plugin depending on it:

```sh
gradlew taskTree --no-repeat assemble

:assemble
+--- :assembleFrontend
|    \--- :installFrontend
|         +--- :installNode
|         \--- :installYarn
+--- :jar
     \--- :classes
          +--- :compileJava
          \--- :processResources
```

1. Considering the frontend assembling script generates the frontend artifacts (HTML, CSS, JS...) in the
`${frontendBuildDir}` directory, these artifacts must be copied, generally in the
`${project.buildDir}/resources/main/public` directory, so as they can be served by the backend.

Let's create a custom task for this. Add the following lines in the `build.gradle` file:

```groovy
tasks.register('processFrontendResources', Copy) {
    description 'Process frontend resources'
    from "${frontendBuildDir}"
    into "${project.buildDir}/resources/main/public"
    dependsOn tasks.named('assembleFrontend')
}
```

Finally, you should:

- Replace the `${frontendBuildDir}` variable by any relevant directory, depending on where the frontend artifacts are
generated by your assembling script.
- Adapt the target directory under `${project.buildDir}/resources/main`, depending on the Java artifact built.

2. The frontend must be assembled and the generated resources must be copied before the backend packaging task.

Our recommendation is the `processResources` task depends on the `processFrontendResources` task.

```groovy
tasks.named('processResources').configure {
    dependsOn tasks.named('processFrontendResources')
}
```

### What kind of script should I attach to the `checkFrontend` task?

The `checkFrontend` task is attached to the lifecycle `check` task. The Gradle official documentation states that the
`check` task shall be used to `attach [...] verification tasks, such as ones that run tests [...]`. It's enough vague to
let you consider any verification task. The script mapped to the `checkFrontend` task may run either automated unit
tests, or functional tests, or a linter, or any other verification action, or even combine some or all of them. Every
combination is even possible, since you can define a script in your `package.json` file that executes sequentially the
actions of your choice.

[contributing]: <CONTRIBUTING.md> (Contributing to this project)
[frontend-maven-plugin]: <https://github.com/eirslett/frontend-maven-plugin> (Frontend Maven plugin)
[gradle]: <https://gradle.org/> (Gradle)
[gradle-base-plugin]: <https://docs.gradle.org/current/userguide/base_plugin.html> (Gradle Base plugin)
[gradle-build-script-block]: <https://docs.gradle.org/current/userguide/plugins.html#sec:applying_plugins_buildscript> (Gradle build script block)
[gradle-dsl]: <https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block> (Gradle DSL)
[gradle-java-plugin]: <https://docs.gradle.org/current/userguide/java_plugin.html> (Gradle Java plugin)
[gradle-spring-boot-plugin]: <https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/> (Gradle Spring Boot plugin)
[jdk]: <https://docs.oracle.com/en/java/javase/> (Java Development Kit)
[node]: <https://nodejs.org/> (Node.js)
[release-notes]: <https://github.com/siouan/frontend-gradle-plugin/releases> (Release notes)
[semantic-versioning]: <https://semver.org/> (Semantic versioning)
[spring-boot]: <https://spring.io/projects/spring-boot> (Spring Boot)
[task-tree]: <task-tree.png>
[yarn]: <https://yarnpkg.com/> (Yarn)
