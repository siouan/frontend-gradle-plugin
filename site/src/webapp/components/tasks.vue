<template>
    <section>
        <!--<h2>Tasks</h2>

        ## Tasks reference

        The plugin registers multiple tasks, that may have dependencies with each other, and also with:
        - Gradle lifecycle tasks defined in the [Gradle Base plugin][gradle-base-plugin]: `clean`, `assemble`, `check`.
        - Tasks defined in the Gradle Publishing plugin: `publish`.

        ### Task tree

        ![Task tree][task-tree]

        ### `installNode` - Install Node.js

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

        ### `installYarn` - Install Yarn

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

        ### `installFrontend` - Install frontend dependencies

        Depending on the value of the `yarnEnabled` property, the `installFrontend` task issues either a `npm install` command
        or a `yarn install` command, by default. If a `package.json` file is found in the directory pointed by the
        `packageJsonDirectory` property, the command shall install/update dependencies and tools for development. Optionally,
        this command may be customized (e.g. to run a `npm ci` command instead of a `npm install` command). To do so, the
        `installScript` property must be set to the corresponding [npm][npm]/[Yarn][yarn] command. This task depends on the
        `installNode` task, and optionally on the `installYarn` task if the `yarnEnabled` property is `true`.

        > This task may be executed directly, e.g. after modifying one of the Node.js/Yarn versions and/or to update frontend
        > dependencies. Otherwise, Gradle executes it if the build requires it.

        **Note about task execution**

        If you execute this task multiple consecutive times with Gradle, you may notice NPM or Yarn always run, and Gradle does
        not skip the task based on a previous succcessful build. This is the expected behavior because the task does not declare
        any input/output so as Gradle knows it is already _UP-TO-DATE_.

        If you think about tweaking this behavior, and skip the task execution on certain circumstances (e.g. declaring the
        `nodes_modules` directory as output), take care of impacts in Gradle. Gradle cannot do magic things with high volume
        input/output directories, and must track each file individually to know if the task must be executed or not. Is it worth
        moving the performance impact in Gradle to skip task execution, or accepting the small overhead of re-running NPM or
        Yarn every time? In a development environment, using the Gradle command line may not be often necessary. On a CI
        workstation where the project is built from scratch after each change, overhead shall be unrelevant because the task
        must be executed anyhow.

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

        **Note about task execution**:

        If you execute directly or indirectly this task with Gradle, you may notice NPM or Yarn runs every time, and Gradle does
        not skip the task based on a previous succcessful build. This is the expected behavior because the task does not declare
        any input/output so as Gradle knows it is already _UP-TO-DATE_.

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
        tasks.register&lt;RunNode&gt;("myCustomScript") {
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
        script = '&#45;&#45;version'
        }
        ```

        - Kotlin syntax:

        ```kotlin
        // build.gradle.kts
        import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx
        tasks.register&lt;RunNpx&gt;("npxVersion") {
            dependsOn(tasks.named("installNode"))
            script.set("&#45;&#45;version")
            }
            ```

            ### Run a custom command with `npm` or `yarn`

            The plugin provides the task type `org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn` that allows
            creating a custom task to run any frontend script. The `script` property must be set with the corresponding
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
            tasks.register&lt;RunNpmYarn&gt;("e2e") {
                dependsOn(tasks.named("installFrontend"))
                script.set("run e2e")
                }
                ```-->
    </section>
</template>
