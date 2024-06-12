<template>
    <FgpTask name="installFrontend">
        <template #title>Install frontend dependencies</template>
        <template #description>
            <p>
                Depending on the value of the <FgpPropertyLink name="yarnEnabled" /> property, this task issues either
                a <FgpCode>npm install</FgpCode> command or a <FgpCode>yarn install</FgpCode> command, by default.
                Consequently, the command shall install/update dependencies, and perform any additional actions as
                described in the <FgpCode>package.json</FgpCode> file located in the directory pointed by the
                <FgpPropertyLink name="packageJsonDirectory" /> property. Optionally, this command may be customized
                with the <FgpPropertyLink name="installScript" /> property (e.g. to run a <FgpCode>npm ci</FgpCode>
                command instead). This task depends on the <FgpTaskLink name="installNode" /> task, and optionally on
                the <FgpTaskLink name="installYarn" /> task if the <FgpPropertyLink name="yarnEnabled" /> property
                is <FgpCode>true</FgpCode>.
            </p>

            <div class="card my-3">
                <div class="card-body">
                    <h5 class="card-title">
                        About <FgpGradleDocsLink path="/current/userguide/incremental_build.html"
                        >incremental build</FgpGradleDocsLink> and up-to-date checks
                    </h5>
                    <p class="card-text">
                        If you execute this task several times in a row, you may notice the
                        <FgpCode>npm</FgpCode>/<FgpCode>yarn</FgpCode> command is always executed: Gradle does not
                        skip the task based on a previous execution with the
                        <FgpGradleTaskOutcomeLink outcome="SUCCESS" /> outcome. This is the expected behaviour
                        because the task does not declare any input/output Gradle could track, to know the task is
                        already <FgpGradleTaskOutcomeLink outcome="UP-TO-DATE" /> - unlike the
                        <FgpTaskLink name="installNode" /> and <FgpTaskLink name="installYarn" /> tasks. Both
                        <FgpNpmLink /> and <FgpYarnLink /> have their own strategy to limit overhead when
                        dependencies are already up to date. The plugin does not duplicate this logic, considering it is
                        the business of each package manager, and it would hardly perform as well. If you are tempted to
                        tweak the task so as its execution is skipped under certain circumstances (e.g. declaring the
                        large <FgpCode>nodes_modules</FgpCode> directory as an output), you would move this logic in
                        Gradle. Gradle cannot do magic with large directories, and would have to track each file
                        individually to determine whether the task should be executed or skipped. The question may be:
                        is it worth moving the overhead to Gradle to skip task execution - maybe, or accepting the one
                        occuring when <FgpCode>npm</FgpCode>/<FgpCode>yarn</FgpCode> is executed again? If you are
                        about to tweak this task, take a look at these
                        <FgpSiteLink path="#tweaking-tasks">recommendations</FgpSiteLink>.
                    </p>
                    <div class="text-muted small">
                        Some related discussions in Gradle forums: [<FgpLink
                            href="https://discuss.gradle.org/t/project-level-build-cache-for-javascript-and-yarn-projects/24134"
                            title="A discussion in Gradle forums"
                        >1</FgpLink>] [<FgpLink
                            href="https://discuss.gradle.org/t/gradle-having-problems-with-large-folders-as-task-inputs-outputs/34775"
                            title="A discussion in Gradle forums"
                        >2</FgpLink>] [<FgpLink
                            href="https://discuss.gradle.org/t/incremental-task-false-up-to-date-result/24619"
                            title="A discussion in Gradle forums"
                        >3</FgpLink>]
                    </div>
                </div>
            </div>
        </template>
    </FgpTask>
</template>
