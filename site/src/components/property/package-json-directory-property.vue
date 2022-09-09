<template>
    <fgp-property
        name="packageJsonDirectory"
        type="java.lang.String"
        default-value="$projectDir"
        :tasks="['resolvePackageManager', 'installPackageManager', 'installFrontend', 'cleanFrontend', 'assembleFrontend', 'checkFrontend', 'publishFrontend']"
    >
        <p>
            Location of the directory containing the <fgp-code>package.json</fgp-code> file. By default, this file is
            considered to be located in the project's directory, at the same level than this
            <fgp-code>build.gradle[.kts]</fgp-code> file. If the <fgp-code>package.json</fgp-code> file is located in
            another directory, it is recommended either to set up a Gradle multi-project build, or to set this property
            with the appropriate directory. This directory being used as the working directory when running JS scripts,
            consequently, the <fgp-code>node_modules</fgp-code> directory would be created at this location after the
            <fgp-task-link name="installFrontend" /> task is executed.
        </p>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpProperty from "./property";
import fgpTaskLink from "../link/task-link";

export default Vue.component("fgp-package-json-directory-property", {
    components: { fgpCode, fgpProperty, fgpTaskLink }
});
</script>
