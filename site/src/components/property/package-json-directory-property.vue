<template>
    <FgpProperty
        name="packageJsonDirectory"
        type="java.lang.String"
        default-value="$projectDir"
        :task-names="[
            'resolvePackageManager',
            'installPackageManager',
            'installFrontend',
            'cleanFrontend',
            'assembleFrontend',
            'checkFrontend',
            'publishFrontend',
        ]"
    >
        <p>
            Location of the directory containing the
            <FgpCode>package.json</FgpCode> file. By default, this file is considered to be located in the project's
            directory, at the same level than this <FgpCode>build.gradle[.kts]</FgpCode> file. If the
            <FgpCode>package.json</FgpCode> file is located in another directory, it is recommended either to set up a
            Gradle multi-project build, or to set this property with the appropriate directory. This directory being
            used as the working directory when running JS scripts, consequently, the
            <FgpCode>node_modules</FgpCode> directory would be created at this location after the
            <FgpTaskLink name="installFrontend" /> task is executed.
        </p>
    </FgpProperty>
</template>
