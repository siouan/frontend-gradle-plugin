<h2 align="center">Frontend Gradle plugin - Integrated <a href="https://nodejs.org/" title="Node.js">Node.js</a>, <a href="https://www.npmjs.com/" title="npm">npm</a>, <a href="https://yarnpkg.com/" title="Yarn">Yarn</a> builds</h2> 
<p align="center">
    <a href="https://github.com/siouan/frontend-gradle-plugin/releases/tag/v3.0.0"><img src="https://img.shields.io/badge/Latest%20release-3.0.0-blue.svg" alt="Latest release 3.0.0"/></a>
    <a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/License-Apache%202.0-green.svg" alt="License Apache 2.0"/></a>
    <br/>
    <a href="https://travis-ci.com/siouan/frontend-gradle-plugin"><img src="https://travis-ci.com/siouan/frontend-gradle-plugin.svg?branch=3.0" alt="Build status"/></a>
    <a href="https://sonarcloud.io/dashboard?id=siouan_frontend-gradle-plugin"><img src="https://sonarcloud.io/api/project_badges/measure?project=siouan_frontend-gradle-plugin&metric=alert_status" alt="Quality gate status"/></a>
    <a href="https://sonarcloud.io/dashboard?id=siouan_frontend-gradle-plugin"><img src="https://sonarcloud.io/api/project_badges/measure?project=siouan_frontend-gradle-plugin&metric=coverage" alt="Code coverage"/></a>
    <a href="https://sonarcloud.io/dashboard?id=siouan_frontend-gradle-plugin"><img src="https://sonarcloud.io/api/project_badges/measure?project=siouan_frontend-gradle-plugin&metric=reliability_rating" alt="Reliability"/></a>
</p>

This plugin allows to integrate a [Node.js][nodejs]/[npm][npm]/[Yarn][classic-yarn] build into [Gradle][gradle]. It is
inspired by the philosophy of the [frontend-maven-plugin][frontend-maven-plugin]. See the
[quick start guide](#quick-start-guide) below to install/configure the plugin, and build your frontend application.

#### Key features

- **Distribution management**: the plugin downloads and installs a [Node.js][nodejs] distribution and/or a
[Yarn][classic-yarn] distribution when required. Optionally, a shared/global distribution may be used instead to avoid
network overhead and duplication. The plugin may also use a HTTP proxy server for downloads, to take advantage of any
caching facility, and submit to the organization's security rules. Basic authentication scheme is supported for both
distribution and proxy servers.
- **Configurable dependencies installation**: depending on the environment, installing frontend dependencies using the
`package.json` file may require a different command (e.g. `npm ci`).
- **Built-in tasks**: no need to define tasks to build, clean, check, or publish the frontend application through
Gradle lifecycle. The plugin provides them out of the box, and ensures their implementation matches Gradle's
[recommendations][gradle-task-configuration-avoidance]. Plug scripts from a `package.json` file with the
[DSL](#dsl-reference), and run `gradlew build`.
- **Customization**: for more complex use cases, the plugin provides types to create tasks and run custom commands with
[Node.js][nodejs], [npm][npm], [npx][npx], [Yarn][classic-yarn].

#### Under the hood

- **Lazy configuration**: tasks configuration is delayed until necessary thanks to the use of Gradle
[lazy configuration API][gradle-lazy-configuration], to optimize performance of builds and ease chaining tasks I/O.
- **Self-contained domain design**: the plugin design is influenced by [clean coding][clean-coder] principles.
The domain layer is isolated from any framework and infrastructure. Writing cross-platform unit tests and maintaining
the plugin is easier. Code coverage and predictability increase.

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
      - [Table of properties](#table-of-properties)
      - [Configuration blocks](#configuration-blocks)
    - [Examples][examples]
        - [Build a frontend application in a single project][example-single-project]
        - [Build a frontend application in a single project using preinstalled distributions][example-single-project-preinstalled-distributions]
        - [Build multiple frontend applications with dedicated sub-projects using shared distributions][example-multi-projects-applications]
        - [Build a full-stack Spring Boot WAR application with dedicated frontend and backend sub-projects][example-multi-projects-war-application]
  - [Final steps](#final-steps)
    - [Build the frontend application](#build-the-frontend-application)
    - [Use Node.js/npm/npx/Yarn apart from Gradle](#use-nodejsnpmnpxyarn-apart-from-gradle)
  - [Recommendations](#recommendations)
    - [Using `*Script` properties](#using-script-properties)
    - [Customizing built-in tasks](#customizing-built-in-tasks)
- [Tasks reference](#tasks-reference)
  - [Task tree](#task-tree)
  - [`installNode` - Install Node.js](#installnode---install-nodejs)
  - [`installYarn` - Install Yarn](#installyarn---install-yarn)
  - [`installFrontend` - Install frontend dependencies](#installfrontend---install-frontend-dependencies)
  - [`cleanFrontend` - Clean frontend project](#cleanfrontend---clean-frontend-project)
  - [`assembleFrontend` - Assemble frontend artifacts](#assemblefrontend---assemble-frontend-artifacts)
  - [`checkFrontend` - Check frontend application](#checkfrontend---check-frontend-application)
  - [`publishFrontend` - Publish frontend artifacts](#publishfrontend---publish-frontend-artifacts)
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
- [Yarn][classic-yarn] 1.0+ ([Yarn 2][yarn] not supported)

The plugin is built and tested on Linux, Mac OS, Windows. See the [contributing notes][contributing] to know the list of
build environments used.

### Installation

Follow instructions provided by the Gradle Plugin Portal [here][gradle-plugin-page].

### Configuration

#### DSL reference

##### Table of properties

The table below introduces each property in details. You may also take a look at [Tasks reference](#tasks-reference)
section for further information. **Bold case** highlights required properties, whose value cannot be `null`.

<table>
<tbody>
<tr>
    <th scope="col" align="left">Property</th>
    <th scope="col" align="left">Description</th>
</tr>
<tr>
    <th scope="col" align="center" colspan="2">NODE.JS SETTINGS</th>
</tr>
<tr>
    <td align="left"><b><code>nodeDistributionProvided</code></b></td>
    <td align="left">
<b>Default value</b>: <code>false</code><br/>
<br/>
Whether the Node distribution is already provided, and shall not be downloaded. When <code>true</code>, the plugin
relies on the following locations in this exact order to find node/npm/npx executables:
<ul>
<li>The directory pointed by the <code>nodeInstallDirectory</code> property, if set.</li>
<li>The directory pointed by the <code>NODEJS_HOME</code> environment variable, if set.</li>
<li>Any directory in the <code>PATH</code> environment variable.</li>
</ul>
Other <code>node*</code> properties should not be used for clarity. When <code>false</code>, at least the
<code>nodeVersion</code> property must be set.
    </td>
</tr>
<tr>
    <td align="left"><code>nodeVersion</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<b>Example</b>: <code>'12.16.3'</code><br/>
<br/>
Version number used to build the exact URL to download the distribution. The property is required when the
<code>nodeDistributionProvided</code> property is <code>false</code>.
    </td>
</tr>
<tr>
    <td align="left"><b><code>nodeDistributionUrlRoot</code></b></td>
    <td align="left">
<b>Default value</b>: <code>'https://nodejs.org/dist/'</code><br/>
<br/>
This property is used to build the exact URL to download the distribution, by appending the value of the
<code>nodeDistributionUrlPathPattern</code> property to its value. By default, the plugin attempts to download the
distribution from Node.js' website.
    </td>
</tr>
<tr>
    <td align="left"><b><code>nodeDistributionUrlPathPattern</code></b></td>
    <td align="left">
<b>Default value</b>: <code>'vVERSION/node-vVERSION-ARCH.TYPE'</code><br/>
<br/>
This property is used to build the exact URL to download the distribution, by appending its value to the value of the
<code>nodeDistributionUrlRoot</code> property. This property may be set with a fixed trailing path part (e.g.
<code>dist/node-v12.16.3-win-x64.zip</code>), or take advantage of the automatic distribution resolution in the plugin, using
specific tokens in the pattern such as <code>VERSION</code>, <code>ARCH</code>, <code>TYPE</code>. See the
<code><a href="#installnode---install-nodejs">installNode</a></code> task for details about when and how to use this
token.<br/>
Then, the exact URL used by default to download the distribution is
<code>https://nodejs.org/dist/vVERSION/node-vVERSION-ARCH.TYPE</code>.
    </td>
</tr>
<tr>
    <td align="left"><code>nodeDistributionServerUsername</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<br/>
This property may be used to download the distribution with a given identity (BASIC scheme server-side). When not
<code>null</code>, the <code>nodeDistributionServerPassword</code> property is also required. 
</td>
</tr>
<tr>
    <td align="left"><code>nodeDistributionServerPassword</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<br/>
Set this property to authenticate the distribution download (BASIC scheme server-side). The property is ignored unless
the <code>nodeDistributionServerUsername</code> property is defined.
</td>
</tr>
<tr>
    <td align="left"><code>nodeInstallDirectory</code></td>
    <td align="left">
<b>Default value</b>: <code>file("${projectDir}/node")</code><br/>
<br/>
Install directory where the distribution archive shall be exploded, or where a provided distribution is located.
    </td>
</tr>
<tr>
    <th scope="col" align="center" colspan="2">YARN SETTINGS</th>
</tr>
<tr>
    <td align="left"><b><code>yarnEnabled</code></b></td>
    <td align="left">
<b>Default value</b>: <code>false</code><br/>
<br/>
Whether Yarn shall be used instead of npm when executing frontend tasks. Consequently, a Yarn distribution may be
downloaded and installed by the plugin, or may be provided on the local platform.
</td>
</tr>
<tr>
    <td align="left"><b><code>yarnDistributionProvided</code></b></td>
    <td align="left">
<b>Default value</b>: <code>false</code><br/>
<br/>
Whether the Yarn distribution is already provided, and shall not be downloaded. When <code>true</code>, the plugin
relies on the following locations in this exact order to find the <code>yarn</code> executable:
<ul>
<li>The directory pointed by the <code>yarnInstallDirectory</code> property, if not <code>null</code>.</li>
<li>The directory pointed by the <code>YARN_HOME</code> environment variable, if not <code>null</code>.</li>
<li>Any directory in the <code>PATH</code> environment variable.</li>
</ul>
Other <code>yarn*</code> properties should not be used for clarity. When <code>false</code>, at least the
<code>yarnVersion</code> property must be set.
    </td>
</tr>
<tr>
    <td align="left"><code>yarnVersion</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<b>Example</b>: <code>'1.22.4'</code><br/>
<br/>
Version number used to build the exact URL to download the distribution. The property is required when the
<code>yarnEnabled</code> property is <code>true</code>, and the <code>yarnDistributionProvided</code> property is
<code>false</code>.
    </td>
</tr>
<tr>
    <td align="left"><b><code>yarnDistributionUrlRoot</code></b></td>
    <td align="left">
<b>Default value</b>: <code>'https://github.com/yarnpkg/yarn/releases/download/'</code><br/>
<br/>
This property is used to build the exact URL to download the distribution, by appending the value of the
<code>yarnDistributionUrlPathPattern</code> property to its value. By default, the plugin attempts to download the
distribution from Yarn's website.
    </td>
</tr>
<tr>
    <td align="left"><b><code>yarnDistributionUrlPathPattern</code></b></td>
    <td align="left">
<b>Default value</b>: <code>'vVERSION/yarn-vVERSION.tar.gz'</code><br/>
<br/>
This property is used to build the exact URL to download the distribution, by appending its value to the value of the
<code>yarnDistributionUrlRoot</code> property. This property may be set with a fixed trailing path part (e.g.
<code>dist/yarn-v1.22.4.tar.gz</code>), or take advantage of the automatic distribution resolution in the plugin, using
specific tokens in the pattern such as <code>VERSION</code>. See the
<code><a href="#installyarn---install-yarn">installYarn</a></code> task for details about when and how to use this
token.<br/>
Then, the exact URL used by default to download the distribution is
<code>https://github.com/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz</code>.
    </td>
</tr>
<tr>
    <td align="left"><code>yarnDistributionServerUsername</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<br/>
This property may be used to download the distribution with a given identity (BASIC scheme server-side). When not
<code>null</code>, the <code>yarnDistributionServerPassword</code> property is also required. 
</td>
</tr>
<tr>
    <td align="left"><code>yarnDistributionServerPassword</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<br/>
Set this property to authenticate the distribution download (BASIC scheme server-side). The property is ignored unless
the <code>yarnDistributionServerUsername</code> property is defined.
</td>
</tr>
<tr>
    <td align="left"><code>yarnInstallDirectory</code></td>
    <td align="left">
<b>Default value</b>: <code>file("${projectDir}/yarn")</code><br/>
<br/>
Install directory where the distribution archive shall be exploded, or where a provided distribution is located.
    </td>
</tr>
<tr>
    <th scope="col" align="center" colspan="2">SCRIPT SETTINGS</th>
</tr>
<tr>
    <td align="left" colspan="2">
Name of npm/Yarn scripts (see <code>package.json</code> file) that shall be executed depending
on this plugin's task. The values below are passed as arguments of npm/yarn executables. Under Unix-like O/S, white
space characters ' ' in an argument value must be escaped with a backslash character '\'. Under Windows O/S, the whole
argument must be enclosed between double-quotes.<br/>
<b>Example on Unix-like O/S</b>: <code>assembleScript = 'run assemble single\ argument'</code><br/>
<b>Example on Windows O/S</b>: <code>assembleScript = 'run assemble "single argument"'</code>
    </td>
</tr>
<tr>
    <td align="left"><b><code>installScript</code></b></td>
    <td align="left">
<b>Default value</b>: <code>'install'</code><br/>
<br/>
Use this property to customize the command line used to install frontend dependencies. This property is used by the
<code>installFrontend</code> task.
    </td>
</tr>
<tr>
    <td align="left"><code>cleanScript</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<b>Example</b>: <code>'run clean'</code><br/>
<br/>
Use this property only if frontend's compiled resources are generated out of the <code>${project.buildDir}</code>
directory. This property is used by the <code>cleanFrontend</code> task. Apart from direct execution, the task is also
executed when the Gradle built-in <code>clean</code> task is executed.
    </td>
</tr>
<tr>
    <td align="left"><code>assembleScript</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<b>Example</b>: <code>'run assemble'</code><br/>
<br/>
Script called to build frontend's artifacts. This property is used by the <code>assembleFrontend</code> task. Apart from
direct execution, the task is also executed when the Gradle built-in <code>assemble</code> task is executed.
    </td>
</tr>
<tr>
    <td align="left"><code>checkScript</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<b>Example</b>: <code>'run check'</code><br/>
<br/>
Script called to check the frontend application. This property is used by the <code>checkFrontend</code> task. Apart
from direct execution, the task is also executed when the Gradle built-in <code>check</code> task is executed.
    </td>
</tr>
<tr>
    <td align="left"><code>publishScript</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<b>Example</b>: <code>'run publish'</code><br/>
<br/>
Script called to publish the frontend artifacts. This property is used by the <code>publishFrontend</code> task. Apart
from direct execution, the task is also executed when the Gradle built-in <code>publish</code> task is executed.
    </td>
</tr>
<tr>
    <th scope="col" align="center" colspan="2">OTHER SETTINGS</th>
</tr>
<tr>
    <td align="left"><b><code>packageJsonDirectory</code></b></td>
    <td align="left">
<b>Default value</b>: <code>projectDir</code><br/>
<br/>
Location of the directory containing the <code>package.json</code> file. By default, this file is considered to be
located in the project's directory, at the same level than this <code>build.gradle[.kts]</code> file. If the
<code>package.json</code> file is located in another directory, it is recommended either to set up a Gradle
multi-project build, or to set this property with the appropriate directory. This directory being used as the working
directory when running JS scripts, consequently, the <code>node_modules</code> directory would be created at this
location after the <code>installFrontend</code> task is executed.
    </td>
</tr>
<tr>
    <td align="left"><code>proxyHost</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<b>Example</b>: <code>'127.0.0.1'</code><br/>
<br/>
IP address or domain name of a HTTP proxy server to use when downloading distributions. By default, the plugin uses
direct connections.
    </td>
</tr>
<tr>
    <td align="left"><code>proxyPort</code></td>
    <td align="left">
<b>Default value</b>: <code>8080</code><br/>
<br/>
Port of the proxy server. This property is ignored unless the <code>proxyHost</code> property is defined.
    </td>
</tr>
<tr>
    <td align="left"><code>proxyUsername</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<br/>
This property may be used to authenticate on the proxy server with a given identity (BASIC scheme server-side). This
property is ignored unless the <code>proxyHost</code> property is defined. When not <code>null</code>, the
<code>proxyPassword</code> property is also required. 
</td>
</tr>
<tr>
    <td align="left"><code>proxyPassword</code></td>
    <td align="left">
<b>Default value</b>: <code>null</code><br/>
<br/>
Set this property to authenticate the distribution download (BASIC scheme server-side). The property is ignored unless
the <code>proxyUsername</code> property is defined.
</td>
</tr>
<tr>
    <td align="left"><b><code>verboseModeEnabled</code></b></td>
    <td align="left">
<b>Default value</b>: <code>false</code><br/>
<br/>
Whether the plugin shall log additional messages whatever Gradle's logging level is. Technically speaking, messages
logged by the plugin with the INFO level are made visible. This property allows to track the plugin execution without
activating Gradle's INFO or DEBUG levels, that may be too much verbose on a global point of view.
    </td>
</tr>
</tbody>
</table>

##### Configuration blocks

The plugin declares an extension to Gradle DSL, named `frontend`. Declare a `frontend` block in the `build.gradle[.kts]`
file, and setup the build with the plugin DSL.

- Groovy syntax:

```groovy
// build.gradle
frontend {
    nodeDistributionProvided = false
    nodeVersion = '12.16.3'
    nodeDistributionUrlRoot = 'https://nodejs.org/dist/'
    nodeDistributionUrlPathPattern = 'vVERSION/node-vVERSION-ARCH.TYPE'
    nodeDistributionServerUsername = 'username'
    nodeDistributionServerPassword = 'password'
    nodeInstallDirectory = file("${projectDir}/node")

    yarnEnabled = false
    yarnDistributionProvided = false
    yarnVersion = '1.22.4'
    yarnDistributionUrlRoot = 'https://github.com/yarnpkg/yarn/releases/download/'
    yarnDistributionUrlPathPattern = 'vVERSION/yarn-vVERSION.tar.gz'
    yarnDistributionServerUsername = 'username'
    yarnDistributionServerPassword = 'password'
    yarnInstallDirectory = file("${projectDir}/yarn")

    installScript = 'install'
    cleanScript = 'run clean'
    assembleScript = 'run assemble'
    checkScript = 'run check'
    publishScript = 'run publish'

    packageJsonDirectory = projectDir
    proxyHost = '127.0.0.1'
    proxyPort = 8080
    proxyUsername = 'username'
    proxyPassword = 'password'
    verboseModeEnabled = false
}
```

- Kotlin syntax:

```kotlin
// build.gradle.kts
frontend {
    nodeDistributionProvided.set(false)
    nodeVersion.set("12.16.3")
    nodeDistributionUrlRoot.set("https://nodejs.org/dist/")
    nodeDistributionUrlPathPattern.set("vVERSION/node-vVERSION-ARCH.TYPE")
    nodeDistributionServerUsername.set("username")
    nodeDistributionServerPassword.set("password")
    nodeInstallDirectory.set(project.layout.projectDirectory.dir("node"))

    yarnEnabled.set(false)
    yarnDistributionProvided.set(false)
    yarnVersion.set("1.22.4")
    yarnDistributionUrlRoot.set("https://github.com/yarnpkg/yarn/releases/download/")
    yarnDistributionUrlPathPattern.set("vVERSION/yarn-vVERSION.tar.gz")
    yarnDistributionServerUsername.set("username")
    yarnDistributionServerPassword.set("password")
    yarnInstallDirectory.set(project.layout.projectDirectory.dir("yarn"))

    installScript.set("install")
    cleanScript.set("run clean")
    assembleScript.set("run assemble")
    checkScript.set("run check")
    publishScript.set("run publish")

    packageJsonDirectory.set(project.layout.projectDirectory)
    proxyHost.set("127.0.0.1")
    proxyPort.set(8080)
    proxyUsername.set("username")
    proxyPassword.set("password")
    verboseModeEnabled.set(false)
}
```

#### Examples

See examples introduced [here][examples].

### Final steps

#### Build the frontend application

Now that the plugin is correctly installed and configured, open a terminal, and execute the following command in the
project's directory:

```sh
gradlew build
```

#### Use Node.js/npm/npx/Yarn apart from Gradle
 
If you plan to use the downloaded distributions of [Node.js][nodejs]/[npm][npm]/[npx][npx] or [Yarn][classic-yarn] apart
from Gradle, apply the following steps:

- Create a `NODEJS_HOME` environment variable containing the real path set in the `nodeInstallDirectory` property.
- Add the Node/npm executables' directory to the `PATH` environment variable:
  - On Unix-like O/S, add the `$NODEJS_HOME/bin` path.
  - On Windows O/S, add `%NODEJS_HOME%` path.

Optionally, if Yarn is enabled and you don't want to enter Yarn's executable absolute path on a command line:

- Create a `YARN_HOME` environment variable containing the real path set in the `yarnInstallDirectory` property.
- Add the Yarn executable's directory to the `PATH` environment variable:
  - On Unix-like O/S, add the `$YARN_HOME/bin` path.
  - On Windows O/S, add the `%YARN_HOME%\bin` path.

### Recommendations

#### Using `*Script` properties

Design of the plugin's tasks running a [Node.js][nodejs]/[npm][npm]/[npx][npx]/[Yarn][classic-yarn] command (e.g.
`assembleFrontend` task) rely on the assumption the `package.json` file contains all definitions of the frontend build
actions, and is the single resource defining how to build the frontend application, execute unit tests, lint source
code, run a development server, publish artifacts... We recommend to keep these definitions in this file, in the
`scripts` section, and avoid as much as possible using the plugin's `*Script` properties to run complex commands. Keep
the frontend build definitions in one place, and let everyone easily finds out where they are located. In an ideal
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

#### Customizing built-in tasks

If you need to customize the plugin's built-in tasks (e.g. declare additional I/O or dependencies), it is very important
to conform to the [Configuration avoidance API][gradle-configuration-avoidance-api]: use references of task providers
instead of references of tasks, and continue taking advantage of the lazy configuration behavior the plugin already
implements. The examples below introduce the implementation expected with simple cases:

```groovy
// Configuring a predefined task.
// LEGACY SYNTAX: task 'installFrontend' is immediately created and configured, as well as task
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

If your application uses the legacy syntax, you may find further instructions to migrate to the modern syntax in this
Gradle's [guide][gradle-migration-guide].

## Tasks reference

The plugin registers multiple tasks, that may have dependencies with each other, and also with:
- Gradle lifecycle tasks defined in the [Gradle Base plugin][gradle-base-plugin]: `clean`, `assemble`, `check`.
- Tasks defined in the Gradle Publishing plugin: `publish`.

### Task tree

![Task tree][task-tree]

### `installNode` - Install Node.js

The `installNode` task downloads a [Node.js][nodejs] distribution and verifies its integrity. By default, the exact
download URL is guessed using the `nodeVersion` property, the `nodeDistributionUrlRoot` property, the
`nodeDistributionUrlPathPattern` property, and the current platform's architecture. Checking the distribution integrity
consists of downloading a file providing the distribution shasum. This file is expected to be in the same remote web
directory than the distribution. For example, if the distribution is located at URL
`https://nodejs.org/dist/v12.16.3/node-v12.16.3-win-x64.zip`, the plugin attempts to download the shasum file located at
URL `https://nodejs.org/dist/v12.16.3/SHASUMS256.txt`. Optionally, defining the `proxyHost` property and the `proxyPort`
property allows to use a proxy server when downloading the distribution and the shasum. The `proxyUsername` and
`proxyPassword` properties may also be used to authenticate on the proxy server with a BASIC scheme. Set the location
where the distribution shall be installed with the `nodeInstallDirectory` property, which by default is the
`${projectDir}/node` directory.

If a [Node.js][nodejs] distribution is already installed in the system, and shall be used instead of a downloaded
distribution, set the `nodeDistributionProvided` property to `true` and the location of the distribution with the
`nodeInstallDirectory` property. Alternatively, this latter property may not be set, and the plugin would locate the
distribution using the `NODEJS_HOME` environment variable, or the `PATH` environment variable. Consequently, the
`installNode` task is automatically _SKIPPED_ during a Gradle build. The `nodeVersion` property and the
`nodeDistributionUrl*` properties are useless and shall not be set for clarity. 

The task takes advantage of [Gradle incremental build][gradle-incremental-build], and is not executed again unless one
of its inputs/outputs changed. The task is _UP-TO-DATE_ during a Gradle build, and skipped.

> This task should not be executed directly. Gradle executes it if the build requires it.

**About the distribution download URL**

The `nodeDistributionUrlRoot` property and the `nodeDistributionUrlPathPattern` property offer a convenient way to
download distributions from a custom website - e.g. a website mirroring the official Node.js' website, and still take
advantage of the automatic resolution of the exact URL, based on the current platform's architecture. The
`yarnDistributionUrlPathPattern` property may contain the following tokens:
- `VERSION`: automatically replaced with the version number set in the `nodeVersion` property.
- `ARCH`: automatically replaced with the architecture ID matching the current platform. For example, if the current O/S
is MacOS 64 bits, the plugin replaces the token with `darwin-x64`.
- `TYPE`:  automatically replaced with the type of the distribution file expected for the current platform. For example,
if the current O/S is Windows, the plugin replaces the token with `zip`.

When using a custom website, the `nodeDistributionServerUsername` and `nodeDistributionServerPassword` properties may
also be used to authenticate with a BASIC scheme. Then, if the distribution needs to be upgraded and tokens are
used appropriately in the `nodeDistributionUrlPathPattern` property, update only the `nodeVersion` property and the
plugin will download the relevant distribution from the custom website.

### `installYarn` - Install Yarn

The `installYarn` task downloads a [Yarn][classic-yarn] distribution, if `yarnEnabled` property is `true`. By default,
the exact download URL is guessed using the `yarnVersion` property, the `yarnDistributionUrlRoot` property, and the
`yarnDistributionUrlPathPattern` property.
Optionally, defining the `proxyHost` property and the `proxyPort` property allow to use a proxy server when downloading
the distribution. The `proxyUsername` and `proxyPassword` properties may also be used to authenticate on the proxy
server with a BASIC scheme. Set the location where the distribution shall be installed with the `yarnInstallDirectory`
property, which by default is the `${projectDir}/yarn` directory.

If a [Yarn][classic-yarn] distribution is already installed in the system, and shall be used instead of a downloaded
distribution, set the `yarnDistributionProvided` property to `true` and the location of the distribution with the
`yarnInstallDirectory` property. Alternatively, this latter property may not be set, and the plugin would locate the
distribution using the `YARN_HOME` environment variable, or the `PATH` environment variable. Consequently, the
`installYarn` task is automatically _SKIPPED_ during a Gradle build. The `yarnEnabled` property still must be `true`,
the `yarnVersion` property and the `yarnDistributionUrl*` properties are useless and shall not be set for clarity. 

The task takes advantage of [Gradle incremental build][gradle-incremental-build], and is not executed again unless one
of its inputs/outputs changed. The task is _UP-TO-DATE_ during a Gradle build, and skipped.

> This task should not be executed directly. Gradle executes it if the build requires it.

**About the distribution download URL**

The `yarnDistributionUrlRoot` property and the `yarnDistributionUrlPathPattern` property offer a convenient way to
download distributions from a custom website - e.g. a website mirroring the official Yarn's website, and still take
advantage of the automatic resolution of the exact URL, based on the version number. The
`yarnDistributionUrlPathPattern` property may contain the `VERSION` token, automatically replaced with the version
number set in the `yarnVersion` property.

When using a custom website, the `yarnDistributionServerUsername` and `yarnDistributionServerPassword` properties may
also be used to authenticate with a BASIC scheme. If the distribution needs to be upgraded and the `VERSION` token is
used appropriately in the `yarnDistributionUrlPathPattern` property, update only the `yarnVersion` property and the
plugin will download the relevant distribution from the custom website.

### `installFrontend` - Install frontend dependencies

Depending on the value of the `yarnEnabled` property, the `installFrontend` task issues either a `npm install` command
or a `yarn install` command, by default. If a `package.json` file is found in the directory pointed by the
`packageJsonDirectory` property, the command shall install/update dependencies and tools for development. Optionally,
this command may be customized (e.g. to run a `npm ci` command instead of a `npm install` command). To do so, the
`installScript` property must be set to the corresponding [npm][npm]/[Yarn][classic-yarn] command. This task depends on the
`installNode` task, and optionally on the `installYarn` task if the `yarnEnabled` property is `true`.

> This task may be executed directly, e.g. after modifying one of the Node.js/Yarn versions and/or to update frontend
> dependencies. Otherwise, Gradle executes it if the build requires it.

**About task execution**

If you execute this task with consecutive Gradle builds, you may notice NPM or Yarn always run, and Gradle does not skip
the task based on a previous succcessful build. This is the expected behavior because the task does not declare any
input/output so as Gradle knows it is already _UP-TO-DATE_.

If you think about tweaking this behavior, and skip the task execution on certain circumstances (e.g. declaring the
`nodes_modules` directory as output), take care of impacts in Gradle. Gradle cannot do magic things with high volume
input/output directories, and must track each file individually to know if the task must be executed or not. Is it worth
moving the performance impact in Gradle to skip task execution, or accepting the small overhead of re-running NPM or
Yarn every time? During development, some people would rather use direct npm/yarn CLI to start a development server, or
run tests. On a CI workstation where the project is built from scratch after each change, overhead shall be unrelevant
because the task would be required anyhow.

The plugin is designed with the requirement the task has a consistent and reliable behavior whatever the Javascript
runtimes used underneath. If it is not acceptable, feel free to declare additional inputs/outputs, with the help of some
[recommendations](#recommendations).

_Some related discussions in Gradle forums_: \[[1][reference-1]] \[[2][reference-2]] \[[3][reference-3]]

### `cleanFrontend` - Clean frontend project

The `cleanFrontend` task does nothing by default, considering frontend generated artifacts (final Javascript, CSS, HTML
files...) are written in the `${project.buildDir}` directory. If it is not the case, this task may be
useful to clean the relevant directory. To do so, a clean script must be defined in the `package.json` file,
and the `cleanScript` property must be set to the corresponding npm/Yarn command. This task depends on the
`installFrontend` task, and is skipped if the `cleanScript` property is not set.

### `assembleFrontend` - Assemble frontend artifacts

The `assembleFrontend` task shall be used to integrate a frontend's build script into Gradle builds. The build script
must be defined in the `package.json` file, and the `assembleScript` property must be set to the corresponding
npm/Yarn command. This task depends on the `installFrontend` task, and is skipped if the `assembleScript` property is
not set.

**About task execution**

If you execute this task with consecutive Gradle builds, you may notice NPM or Yarn always run, and Gradle does not skip
the task based on a previous succcessful build. This is the expected behavior because the task does not declare any
input/output so as Gradle knows it is already _UP-TO-DATE_.

Why the task has no inputs/outputs? This task provides the ability to connect the developer's own Javascript build
process to Gradle, and nothing more. Every Javascript build process is unique. It depends on the project, the language
(e.g. TypeScript, JSX, ECMA script, SASS, SCSS...), the directory layout, the build utilities (Webpack...), etc, chosen
by the team. Moreover, some build utilities are already able to build frontend artifacts incrementally. Consequently,
and actually, it does not make sense to duplicate such behavior in the plugin. If it is not acceptable, feel free to
declare additional inputs/outputs, with the help of some [recommendations](#recommendations).

### `checkFrontend` - Check frontend application

The `checkFrontend` task may be used to integrate a frontend check script into a Gradle build. The check script must be
defined in the project's `package.json` file, and the `checkscript` property must be set with the corresponding
npm/Yarn command. A typical check script defined in the project's `package.json` file may lint frontend source files,
execute tests, and perform additional analysis tasks. This task depends on the `installFrontend` task, and is skipped if
the `checkScript` property is not set.

### `publishFrontend` - Publish frontend artifacts

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

The plugin provides the task type `org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn` that allows
creating a custom task to run any frontend script. The `script` property must be set with the corresponding
[npm][npm]/[Yarn][classic-yarn] command.

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

@[andreaschiona](https://github.com/andreaschiona),
@[byxor](https://github.com/byxor),
@[ChFlick](https://github.com/ChFlick),
@[ckosloski](https://github.com/ckosloski),
@[davidkron](https://github.com/davidkron),
@[ludik0](https://github.com/ludik0),
@[mike-howell](https://github.com/mike-howell),
@[napstr](https://github.com/napstr),
@[nitzan-n](https://github.com/nitzan-n),
@[nuth](https://github.com/nuth),
@[rolaca11](https://github.com/rolaca11),
@[TapaiBalazs](https://github.com/TapaiBalazs)
@[trohr](https://github.com/trohr)

[classic-yarn]: <https://classic.yarnpkg.com/> (Yarn 1.x)
[clean-coder]: <http://cleancoder.com/> (Clean coder)
[contributing]: <CONTRIBUTING.md> (Contributing to this project)
[example-multi-projects-war-application]: <examples/multi-projects-war-application> (Build a full-stack Spring Boot WAR application with dedicated frontend and backend sub-projects)
[example-multi-projects-applications]: <examples/multi-projects-applications-shared-distributions> (Build multiple frontend applications with dedicated sub-projects using shared distributions)
[example-single-project]: <examples/single-project-application> (Build a frontend application in a single project)
[example-single-project-preinstalled-distributions]: <examples/single-project-application-preinstalled-distributions> (Build a frontend application in a single project using preinstalled distributions)
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
[npm-ci]: <https://docs.npmjs.com/cli/ci> (Install a project with a clean state)
[npm-icon]: <resources/npm-icon.png> (NPM)
[npm-install]: <https://docs.npmjs.com/cli/install> (Install a package)
[npx]: <https://github.com/npm/npx> (npx)
[reference-1]: <https://discuss.gradle.org/t/project-level-build-cache-for-javascript-and-yarn-projects/24134> (A discussion in the Gradle forums)
[reference-2]: <https://discuss.gradle.org/t/gradle-having-problems-with-large-folders-as-task-inputs-outputs/34775> (A discussion in the Gradle forums)
[reference-3]: <https://discuss.gradle.org/t/incremental-task-false-up-to-date-result/24619> (A discussion in the Gradle forums)
[release-notes]: <https://github.com/siouan/frontend-gradle-plugin/releases> (Release notes)
[task-tree]: <resources/task-tree.png>
[yarn]: <https://yarnpkg.com/> (Yarn 2)
[yarn-install]: <https://classic.yarnpkg.com/en/docs/cli/install> (Install all dependencies)
[yarn-icon]: <resources/yarn-icon.png> (Yarn)
