# Frontend Gradle plugin

[![GitHub license](https://img.shields.io/github/license/Naereen/StrapDown.js.svg)](https://github.com/Siouan/frontend-gradle-plugin/blob/master/LICENSE)
[![Initial release upcoming](https://img.shields.io/badge/Initial%20release-Upcoming-blue.svg)](https://github.com/Siouan/frontend-gradle-plugin)

This plugin integrates frontend build tasks into a Gradle build. It is inspired by the
[frontend-maven-plugin][frontend-maven-plugin].

Detailed changes for each release are documented in the [release notes][release-notes].

## Summary

- Requirements
- Installation
  - Using Gradle DSL
  - Using Gradle build script block
- Configuration
  - DSL reference
  - Typical configuration with NPM
  - Typical configuration with Yarn
- Tasks
  - Dependencies
  - Install Node
  - Install Yarn
  - Install frontend
  - Clean frontend
  - Assemble frontend
  - Test frontend
  - Start frontend
  - Run custom NPM/Yarn script
- FAQ
- 

## Requirements

The plugin is officially supported with:
- Gradle 5.3+
- JDK 8+ 64 bits

## Installation

2 options are available.

### Using [Gradle DSL][gradle-dsl]

This is the modern and recommended approach.

```gradle
// build.gradle
plugins {
    id 'org.siouan.frontend' version '1.0.0'
}
```

### Using [Gradle build script block][gradle-build-script-block]

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

## Configuration

### DSL reference

All settings are introduced hereafter, with default value for each property.

```gradle
// build.gradle
frontend {
    // Whether Yarn shall be used instead of NPM when executing frontend tasks. Consequently, a distribution shall be
    // downloaded and installed by the plugin. If enabled, the 'YARN SETTINGS' block below must be configured.
    yarnEnabled = false

    // NODE SETTINGS
    // Version of the distribution to download, used to build the URL to download the distribution, if not set.
    version = '10.15.3'

    // [Optional] Sets this property to force the download from a custom website. By default, the plugin attempts to
    // download the distribution compatible with the current platform from the Node website.
    // Note that changing only this property after a build will not trigger a new download during the next build.
    // Use the 'reinstallEnabled' flag to do so.
    distributionUrl = 'https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip'

    // [Optional] Install directory where the distribution archive shall be exploded.
    installDirectory = "${projectDir}/node"

    // YARN SETTINGS
    // Yarn version, used to build the URL to download the corresponding distribution, if not set. This version number
    // is also used to remove the root directory in the distribution archive. If a custom 'distributionUrl' property is
    // set, the version of the distribution is expected to be the same as the one set in the 'version' property, or this
    // may lead to an unexpected result.
    version = '1.15.2'

    // [Optional] Sets this property to force the download from a custom website. By default, the plugin attempts to
    // download the distribution compatible with the current platform Yarn's Github task.
    // Note that changing only this property after a build will not trigger a new download during the next build.
    // Use the 'reinstallEnabled' flag to do so.
    distributionUrl = 'https://github.com/yarnpkg/yarn/releases/download/vX.Y.Z/yarn-vX.Y.Z.tar.gz'

    // [Optional] Install directory where the distribution archive shall be exploded.
    installDirectory = "${projectDir}/yarn"

    // OTHER SETTINGS
    // Name of the NPM/Yarn scripts (see 'package.json' file) that shall be executing depending on the Gradle phase.
    // The value will be passed as an argument of the 'npm' or 'yarn' executable.

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

### Typical configuration with NPM

```gradle
// build.gradle
frontend {
    version = '<X.Y.Z>'
    cleanScript = 'run clean'
    assembleScript = 'run assemble'
    checkScript = 'run check'
}
```

### Typical configuration with Yarn

```gradle
// build.gradle
frontend {
    yarnEnabled = true
    version = '<X.Y.Z>'
    version = '<X.Y.Z>'
    cleanScript = 'run clean'
    assembleScript = 'run assemble'
    checkScript = 'run check'
}
```

## Tasks

The plugin registers multiple tasks, some having dependencies with other, and also with Gradle lifecycle tasks defined
in the [Gradle base plugin][gradle-base-plugin].

### Task dependencies

![Task dependencies][task-dependencies]

### Install Node

The `installNode` task downloads a Node distribution. If the `distributionUrl` property is ommitted, the URL is
guessed using the `version` property. Use the property `nodeInstallationDirectory` to set the directory where the
distribution shall be installed, which, by default is the `${projectDir}/node` directory.
 
### Install Yarn

*Dependencies: **`installYarn`** -> `installNode`*

The `installYarn` task downloads a Yarn distribution, if `yarnEnabled` property is `true`. If the `distributionUrl`
property is ommitted, the URL is guessed using the `version` property. Use the property `yarnInstallationDirectory`
to set the directory where the distribution shall be installed, which, by default is the `${projectDir}/yarn` directory.

### Install frontend dependencies

*Dependencies: **`installFrontend`** -> `installYarn` -> `installNode`*

Depending on the value of the `yarnEnabled` property, the task `installFrontend` issues either a `npm install` command
or a `yarn` command. If a `package.json` file is found in the task's directory, the command shall install
dependencies and tools for frontend development.

### Clean frontend

*Dependencies: `clean` -> **`cleanFrontend`** -> `installFrontend` -> `installYarn` -> `installNode`*

The `cleanFrontend` task does nothing by default, considering frontend generated resources (pre-processed Typescript
files, SCSS stylesheets...) are written in the `${project.buildDir}` directory. If it is not the case, this task may be
useful to clean the relevant directory. To do so, a clean script must be defined in the task's `package.json` file,
and the `cleanScript` property must be set to the corresponding NPM/Yarn command.

### Assemble frontend

*Dependencies: `assemble` -> **`assembleFrontend`** -> `installFrontend` -> `installYarn` -> `installNode`*

The `assembleFrontend` task shall be used to integrate a frontend's build script into Gradle builds. The build script
must be defined in the task's `package.json` file, and the `assembleScript` property must be set to the corresponding
NPM/Yarn command.

### Check frontend

*Dependencies: `check` -> **`checkFrontend`** -> `installFrontend` -> `installYarn` -> `installNode`*

The `checkFrontend` task shall be used to integrate a frontend's check script into Gradle builds. The check script must
be defined in the task's `package.json` file, and the `checkscript` property must be set with the corresponding
NPM/Yarn command. A typical check script defined in the project's `package.json` file may lint frontend source files,
execute tests, and perform additional analysis tasks.

### Run custom NPM/Yarn script

*Dependencies: **`runScriptFrontend`** -> `installFrontend` -> `installYarn` -> `installNode`*

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
