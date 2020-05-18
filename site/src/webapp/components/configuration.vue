<template>
    <section>
        <!--<h2>Configuration</h2>

        <h3>DSL reference</h3>

        <h4>Properties</h4>

        <p>
            All settings are introduced hereafter. Values of optional properties are the default ones applied by the
            plugin, unless otherwise stated. Other values are provided for information. You may also take a look at
            [Tasks reference](#tasks-reference) section for further information.
        </p>

        <ul>
            <li>
                <p>Groovy syntax</p>
                <pre>
// build.gradle
frontend {
    ////// NODE SETTINGS //////
    // [OPTIONAL] Whether the Node distribution is already provided, and shall not be downloaded.
    // The 'nodeInstallDirectory' property shall be used to point the install directory of the
    // distribution, while other 'node*' properties should not be used for clarity.
    // If `false`, at least the 'nodeVersion' property must be set.
    nodeDistributionProvided = false

    // [OPTIONAL] Node version, used to build the URL to download the corresponding distribution, if the
    // 'nodeDistributionUrl' property is not set. By default, this property is 'null'
    nodeVersion = '12.16.1'

    // [OPTIONAL] Set this property to force the download from a custom website. By default, this
    // property is 'null', and the plugin attempts to download the distribution compatible with the
    // current platform from Node's website. The version of the distribution is expected to be the
    // same as the one set in the 'nodeVersion' property, or this may lead to unexpected results.
    nodeDistributionUrl = 'https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip'

    // [OPTIONAL] Install directory where the distribution archive shall be exploded.
    nodeInstallDirectory = file("${projectDir}/node")

    ////// YARN SETTINGS //////
    // [OPTIONAL] Whether Yarn shall be used instead of npm when executing frontend tasks.
    // Consequently, a Yarn distribution will be downloaded and installed by the plugin. If `true`,
    // the 'yarnVersion' property must be set.
    yarnEnabled = false

    // [OPTIONAL] Whether the Yarn distribution is already provided, and shall not be downloaded.
    // The 'yarnInstallDirectory' property shall be used to point the install directory of the
    // distribution, while other 'yarn*' properties should not be used for clarity.
    // If `false`, at least the 'yarnVersion' property must be set.
    yarnDistributionProvided = false

    // [OPTIONAL] Yarn version, used to build the URL to download the corresponding distribution, if
    // the 'yarnDistributionUrl' property is not set. This property is mandatory when the
    // 'yarnEnabled' property is true.
    yarnVersion = '1.22.4'

    // [OPTIONAL] Set this property to force the download from a custom website. By default, this
    // property is 'null', and the plugin attempts to download the distribution compatible with the
    // current platform from Yarn's website. The version of the distribution is expected to be the
    // same as the one set in the 'yarnVersion' property, or this may lead to unexpected results.
    yarnDistributionUrl = 'https://github.com/yarnpkg/yarn/releases/download/vX.Y.Z/yarn-vX.Y.Z.tar.gz'

    // [OPTIONAL] Install directory where the distribution archive shall be exploded.
    yarnInstallDirectory = file("${projectDir}/yarn")

    ////// SCRIPT SETTINGS //////
    // Name of npm/Yarn scripts (see 'package.json' file) that shall be executed depending on this
    // plugin's task. The values below are passed as arguments of the 'npm' or 'yarn' executables.
    // Under Unix-like O/S, white space characters ' ' in an argument value must be escaped with a
    // backslash character '\'. Under Windows O/S, the whole argument must be enclosed between
    // double-quotes. Example: assembleScript = 'run assemble single\ argument'

    // [OPTIONAL] Use this property to customize the command line used to install frontend
    // dependencies. This property is used by the 'installFrontend' task.
    installScript = 'install'

    // [OPTIONAL] Use this property only if frontend's compiled resources are generated out of the
    // '${project.buildDir}' directory. Default value is `null`. This property is used by the
    // 'cleanFrontend' task. Apart from direct execution, the task is also executed when the Gradle
    // built-in 'clean' task is executed.
    cleanScript = 'run clean'

    // [OPTIONAL] Script called to build frontend's artifacts. Default value is `null`. This
    // property is used by the 'assembleFrontend' task. Apart from direct execution, the task is
    // also executed when the Gradle built-in 'assemble' task is executed.
    assembleScript = 'run assemble'

    // [OPTIONAL] Script called to check the frontend application. Default value is `null`. This
    // property is used by the 'checkFrontend' task. Apart from direct execution, the task is also
    // executed when the Gradle built-in 'check' task is executed.
    checkScript = 'run check'

    // [OPTIONAL] Script called to publish the frontend artifacts. Default value is `null`. This
    // property is used by the 'publishFrontend' task. Apart from direct execution, the task is
    // also executed when the Gradle built-in 'publish' task is executed.
    publishScript = 'run publish'

    ////// GENERAL SETTINGS //////
    // [OPTIONAL] Location of the directory containing the 'package.json' file. By default, this
    // file is considered to be located in the project's directory, at the same level than this
    // 'build.gradle[.kts]' file. If the 'package.json' file is located in another directory, it is
    // recommended either to set up a Gradle multi-project build, or to set this property with the
    // appropriate directory. This directory being used as the working directory when running JS
    // scripts, consequently, the 'node_modules' directory would be created at this location after
    // the 'installFrontend' task is executed.
    packageJsonDirectory = projectDir

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
                </pre>
            </li>
            <li>
                <p>Kotlin syntax</p>
                <pre>
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
                </pre>
            </li>
        </ul>

        <h4>Examples</h4>

        <p>
            See examples introduced [here][examples].
        </p>

        <h4>Final steps</h4>

        <h5>Build the frontend application</h5>

        <p>
            Now that the plugin is correctly installed and configured, open a terminal, and execute the following
            command in the project's directory:
        </p>

        <pre>
gradlew build
        </pre>

        <h5>Use Node.js/npm/npx/Yarn apart from Gradle</h5>

        <p>
            If you plan to use the downloaded distributions of [Node.js][nodejs]/[npm][npm]/[npx][npx] or [Yarn][yarn]
            apart from Gradle, apply the following steps:
        </p>

        <ul>
            <li>
                Create a `NODEJS_HOME` environment variable containing the real path set in the `nodeInstallDirectory`
                property.
            </li>
            <li>
                Add the Node/npm executables' directory to the `PATH` environment variable:
                <ul>
                    <li>On Unix-like O/S, add the `$NODEJS_HOME/bin` path.</li>
                    <li>On Windows O/S, add `%NODEJS_HOME%` path.</li>
                </ul>
            </li>
        </ul>

        <p>
            Optionally, if Yarn is enabled and you don't want to enter Yarn's executable absolute path on a command
            line:
        </p>

        <ul>
            <li>
                Create a `YARN_HOME` environment variable containing the real path set in the `yarnInstallDirectory`
                property.
            </li>
            <li>
                Create a `YARN_HOME` environment variable containing the real path set in the `yarnInstallDirectory`
                property.
                <ul>
                    <li>On Unix-like O/S, add the `$YARN_HOME/bin` path.</li>
                    <li>On Windows O/S, add the `%YARN_HOME%\bin` path.</li>
                </ul>
            </li>
        </ul>

        <h4>Recommendations</h4>

        <h5>Using `*Script` properties</h5>

        <p>
            Design of the plugin's tasks running a [Node.js][nodejs]/[npm][npm]/[npx][npx]/[Yarn][yarn] command (e.g.
            `assembleFrontend` task) rely on the assumption the `package.json` file contains all definitions of the
            frontend build actions, and is the single resource defining how to build the frontend application, execute
            unit tests, lint source code, run a development server, publish artifacts... We recommend to keep these
            definitions in this file, in the `scripts` section, and avoid as much as possible using the plugin's
            `*Script` properties to run complex commands. Keep the frontend build definitions in one place, and let
            everyone easily finds out where they are located. In an ideal situation, these properties shall all have a
            value such as `run script-name`, and nothing more. For example:
        </p>

        <pre>
// Instead of:
assembleScript = 'run webpack &#45;&#45; &#45;&#45;config webpack.config.js &#45;&#45;profile'

// Prefer:
assembleScript = 'run build'
// with a package.json file containing:
// "scripts": {
//   "build": "webpack &#45;&#45;config webpack/webpack.prod.js &#45;&#45;profile"
// }
        </pre>

        <h5>Customizing built-in tasks</h5>

        <p>
            If you need to customize the plugin's built-in tasks (e.g. declare additional I/O or dependencies), it is
            very important to conform to the [Configuration avoidance API][gradle-configuration-avoidance-api]: use
            references of task providers instead of references of tasks, and continue taking advantage of the lazy
            configuration behavior the plugin already implements. The examples below introduce the implementation
            expected with simple cases:
        </p>

        <pre>
// Configuring a predefined task.
// FORMER SYNTAX: task 'installFrontend' is immediately created and configured, as well as task
// 'otherTask', even if both tasks are not executed.
installFrontend {
    dependsOn 'otherTask'
    inputs.files('package.json', 'package-lock.json')
}
// MODERN SYNTAX: task 'installFrontend' is created and configured only when Gradle is about to execute it.
// Consequently, task 'otherTask' is also created and configured later.
tasks.named('installFrontend') {
    dependsOn 'otherTask'
    inputs.files('package.json', 'package-lock.json')
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
        </pre>
        <p>
            If your application uses the legacy syntax, you may find further instructions to migrate to the modern
            syntax in this Gradle's [guide][gradle-migration-guide].
        </p>-->
    </section>
</template>
