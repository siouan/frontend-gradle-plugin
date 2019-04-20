# Frontend Gradle plugin

[![Initial release upcoming](https://img.shields.io/badge/Initial%20release-Upcoming-blue.svg)](https://github.com/Siouan/frontend-gradle-plugin)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

[![Build status](https://travis-ci.org/Siouan/frontend-gradle-plugin.svg?branch=master)](https://travis-ci.org/Siouan/frontend-gradle-plugin)
[![Sonarcloud status](https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin)
[![Code coverage](https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=coverage)](https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=Siouan_frontend-gradle-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Siouan_frontend-gradle-plugin)

This plugin integrates frontend build tasks into a Gradle build. It is inspired by the
[frontend-maven-plugin][frontend-maven-plugin].

Detailed changes for each release are documented in the [release notes][release-notes].

## Summary

- [Requirements](#requirements)
- [Installation](#installation)
  - [Activation](#activation)
    - [Using Gradle DSL](#using-gradle-dsl)
    - [Using Gradle build script block](#using-gradle-build-script-block)
  - [Configuration](#configuration)
    - [DSL reference](#dsl-reference)
    - [Typical configuration with NPM](#typical-configuration-with-npm)
    - [Typical configuration with Yarn](#typical-configuration-with-yarn)
  - [Final steps](#final-steps)
- [Tasks](#tasks)
  - [Dependencies](#dependencies)
  - [Install Node](#install-node)
  - [Install Yarn](#install-yarn)
  - [Install frontend dependencies](#install-frontend-dependencies)
  - [Clean frontend](#clean-frontend)
  - [Assemble frontend](#assemble-frontend)
  - [Check frontend](#check-frontend)
  - [Run custom NPM/Yarn script](#run-custom-npmyarn-script)

## Requirements

The plugin is officially supported with:
- Gradle 5.3+
- JDK 8+ 64 bits

## Installation

### Activation 

2 options are available.

#### Using [Gradle DSL][gradle-dsl]

This is the modern and recommended approach.

```gradle
// build.gradle
plugins {
    id 'org.siouan.frontend' version '1.0.0'
}
```

#### Using [Gradle build script block][gradle-build-script-block]

This approach is the legacy way to resolve and apply plugins.

```gradle
// build.gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'org.siouan:frontend-gradle-plugin:1.0.0'
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
    // Whether Yarn shall be used instead of NPM when executing frontend tasks. Consequently, a Yarn distribution will
    // be downloaded and installed by the plugin. If enabled, the 'YARN SETTINGS' block below must be configured.
    yarnEnabled = false

    // NODE SETTINGS
    // Version of the distribution to download, used to build the URL to download the distribution, if not set.
    nodeVersion = '10.15.3'

    // [Optional] Sets this property to force the download from a custom website. By default, this property is 'null',
    // and the plugin attempts to download the distribution compatible with the current platform from the Node website.
    nodeDistributionUrl = 'https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip'

    // [Optional] Install directory where the distribution archive shall be exploded.
    nodeInstallDirectory = "${projectDir}/node"

    // YARN SETTINGS
    // Yarn version, used to build the URL to download the corresponding distribution, if not set. If a custom
    // 'yarnDistributionUrl' property is set, the version of the distribution is expected to be the same as the one set
    // in the 'yarnVersion' property, or this may lead to unexpected results.
    yarnVersion = '1.15.2'

    // [Optional] Sets this property to force the download from a custom website. By default, this property is 'null',
    // and the plugin attempts to download the distribution compatible with the current platform from the Yarn website.
    yarnDistributionUrl = 'https://github.com/yarnpkg/yarn/releases/download/vX.Y.Z/yarn-vX.Y.Z.tar.gz'

    // [Optional] Install directory where the distribution archive shall be exploded.
    yarnInstallDirectory = "${projectDir}/yarn"

    // OTHER SETTINGS
    // Name of the NPM/Yarn scripts (see 'package.json' file) that shall be executing depending on the Gradle lifecycle
    // task. The values below are passed as argument of the 'npm' or 'yarn' executable.

    // [Optional] Use this property only if frontend's compiled resources are generated out of the '${project.buildDir}'
    // directory. Default value is <null>. This property is directly used by the 'cleanFrontend' task. The task is
    // run when the Gradle built-in 'clean' task is run.
    cleanScript = 'run clean'

    // [Optional] Script called to build frontend's artifacts. Default value is <null>. This property is directly used
    // by the 'assembleFrontend' task. The task also run when the Gradle built-in 'assemble' task is run.
    assembleScript = 'run assemble'

    // [Optional] Script called to run frontend's tests. Default value is <null>. This property is directly used by the
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
    yarnEnabled = true
    nodeVersion = '<X.Y.Z>'
    yarnVersion = '<X.Y.Z>'
    cleanScript = 'run clean'
    assembleScript = 'run assemble'
    checkScript = 'run check'
}
```

### Final steps

If the developer needs to use Node/NPM apart from Gradle, it is mandatory to apply the following steps:

- Create a `NODEJS_HOME` environment variable containing the real path set in the `nodeInstallationDirectory`
property.
- Add the `$NODEJS_HOME` (Unix-like OS) or `%NODEJS_HOME%` (Windows OS) path to the `PATH` environment variable.

If Yarn is enabled, apply these latest steps.

- Create a `YARN_HOME` environment variable containing the real path set in the
`yarnInstallationDirectory` property.
- Add the `$YARN_HOME/bin` (Unix-like OS) or `%YARN_HOME%\bin` (Windows OS) path to the `PATH`
environment variable.

## Tasks

The plugin registers multiple tasks, some having dependencies with other, and also with Gradle lifecycle tasks defined
in the [Gradle base plugin][gradle-base-plugin].

### Dependencies

![Task dependencies][task-dependencies]

### Install Node

The `installNode` task downloads a Node distribution. If the `distributionUrl` property is ommitted, the URL is
guessed using the `version` property. Use the property `nodeInstallationDirectory` to set the directory where the
distribution shall be installed, which, by default is the `${projectDir}/node` directory.
 
### Install Yarn

The `installYarn` task downloads a Yarn distribution, if `yarnEnabled` property is `true`. If the `distributionUrl`
property is ommitted, the URL is guessed using the `version` property. Use the property `yarnInstallationDirectory`
to set the directory where the distribution shall be installed, which, by default is the `${projectDir}/yarn` directory.

### Install frontend dependencies

Depending on the value of the `yarnEnabled` property, the task `installFrontend` issues either a `npm install` command
or a `yarn` command. If a `package.json` file is found in the task's directory, the command shall install
dependencies and tools for frontend development.

### Clean frontend

The `cleanFrontend` task does nothing by default, considering frontend generated resources (pre-processed Typescript
files, SCSS stylesheets...) are written in the `${project.buildDir}` directory. If it is not the case, this task may be
useful to clean the relevant directory. To do so, a clean script must be defined in the task's `package.json` file,
and the `cleanScript` property must be set to the corresponding NPM/Yarn command.

### Assemble frontend

The `assembleFrontend` task shall be used to integrate a frontend's build script into Gradle builds. The build script
must be defined in the task's `package.json` file, and the `assembleScript` property must be set to the corresponding
NPM/Yarn command.

### Check frontend

The `checkFrontend` task shall be used to integrate a frontend's check script into Gradle builds. The check script must
be defined in the task's `package.json` file, and the `checkscript` property must be set with the corresponding
NPM/Yarn command. A typical check script defined in the project's `package.json` file may lint frontend source files,
execute tests, and perform additional analysis tasks.

### Run custom NPM/Yarn script

The `runScriptFrontend` task is provided as a task type, to create custom tasks. The `script` property must be set with
the corresponding NPM/Yarn command. For instance, the code below added in the `build.gradle` file allows to run
frontend's end-to-end tests in a custom task:

```gradle
tasks.register('e2e', org.siouan.frontendgradleplugin.RunScriptTask) {
    dependsOn installFrontend
    script = 'run e2e'
}
```

[frontend-maven-plugin]: <https://github.com/eirslett/frontend-maven-plugin> (Frontend Maven plugin)
[gradle-base-plugin]: <https://docs.gradle.org/current/userguide/base_plugin.html> (Gradle Base plugin)
[gradle-build-script-block]: <https://docs.gradle.org/current/userguide/plugins.html#sec:applying_plugins_buildscript> (Gradle build script block)
[gradle-dsl]: <https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block> (Gradle DSL)
[release-notes]: <https://github.com/siouan/frontend-gradle-plugin/releases> (Release notes)
[task-dependencies]: <task-dependencies.png>
