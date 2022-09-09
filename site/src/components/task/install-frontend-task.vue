<template>
    <fgp-task name="installFrontend">
        <template v-slot:title>Install frontend dependencies</template>
        <template v-slot:description>
            <p>
                Depending on the package manager, this task executes either command <fgp-code>npm install</fgp-code>, or
                command <fgp-code>pnpm install</fgp-code>, or command <fgp-code>yarn install</fgp-code>, by default.
                Consequently, the command shall install/update dependencies, and perform any additional actions as
                described in the <fgp-code>package.json</fgp-code> file located in the directory pointed by the
                <fgp-property-link name="packageJsonDirectory" /> property. Optionally, this command may be customized
                with the <fgp-property-link name="installScript" /> property (e.g. to run a <fgp-code>npm ci</fgp-code>
                command instead). On a developer workstation, executing this task is a good starting point to configure
                the workspace for development as it will install the <fgp-nodejs-link /> distribution (if not provided)
                as well as dependencies.
            </p>

            <div class="card my-3">
                <div class="card-body">
                    <h5 class="card-title">About task execution</h5>
                    <p class="card-text">
                        If you execute this task several times in a row, you may notice the
                        <fgp-code>npm</fgp-code>/<fgp-code>pnpm</fgp-code>/<fgp-code>yarn</fgp-code> command is always
                        executed: Gradle does not skip the task based on a previous execution with the
                        <fgp-gradle-task-outcome-link outcome="SUCCESS" /> outcome. This is the expected behaviour
                        because the task does not declare any input/output Gradle could track, to know the task is
                        already <fgp-gradle-task-outcome-link outcome="UP-TO-DATE" /> (e.g. unlike the
                        <fgp-task-link name="installNode" /> task). Both
                        <fgp-npm-link />, <fgp-pnpm-link />, <fgp-yarn-link /> have their own strategy to limit overhead
                        when dependencies are already up to date. The plugin does not duplicate this logic, considering
                        it is the business of each package manager, and it would hardly perform as well. If you are
                        tempted to tweak the task so as its execution is skipped under certain circumstances (e.g.
                        declaring the large <fgp-code>nodes_modules</fgp-code> directory as an output), you would move
                        this logic in Gradle. Gradle cannot do magic with large directories, and would have to track
                        each file individually to determine whether the task should be executed or skipped. The question
                        may be: is it worth moving the overhead to Gradle to skip task execution - maybe, or accepting
                        the one occuring when
                        <fgp-code>npm</fgp-code>/<fgp-code>pnpm</fgp-code>/<fgp-code>yarn</fgp-code> is executed again?
                        If you are about to tweak this task, take a look at these
                        <fgp-site-link path="#tweaking-tasks">recommendations</fgp-site-link>.
                    </p>
                    <div class="text-muted small">
                        Some related discussions in Gradle forums: [<fgp-link
                            href="https://discuss.gradle.org/t/project-level-build-cache-for-javascript-and-yarn-projects/24134"
                            title="A discussion in Gradle forums"
                            >1</fgp-link
                        >] [<fgp-link
                            href="https://discuss.gradle.org/t/gradle-having-problems-with-large-folders-as-task-inputs-outputs/34775"
                            title="A discussion in Gradle forums"
                            >2</fgp-link
                        >] [<fgp-link
                            href="https://discuss.gradle.org/t/incremental-task-false-up-to-date-result/24619"
                            title="A discussion in Gradle forums"
                            >3</fgp-link
                        >]
                    </div>
                </div>
            </div>
        </template>
    </fgp-task>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpGradleTaskOutcomeLink from "../link/gradle-task-outcome-link";
import fgpLink from "../link/link";
import fgpNpmLink from "../link/npm-link";
import fgpPropertyLink from "../link/property-link";
import fgpTask from "./task";
import fgpTaskLink from "../link/task-link";
import fgpYarnLink from "../link/yarn-link";

export default Vue.component("fgp-install-frontend-task", {
    components: {
        fgpCode,
        fgpGradleTaskOutcomeLink,
        fgpLink,
        fgpNpmLink,
        fgpPropertyLink,
        fgpTask,
        fgpTaskLink,
        fgpYarnLink
    }
});
</script>
