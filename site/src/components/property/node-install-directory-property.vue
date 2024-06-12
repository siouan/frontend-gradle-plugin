<template>
    <FgpProperty
        name="nodeInstallDirectory"
        type="java.io.File"
        :required="false"
        default-value="project.layout.projectDirectory.dir(&quot;node&quot;)"
        :task-names="[
            'installNode',
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
            Directory where the <FgpNodejsLink /> distribution shall be installed or is already installed (depending on
            the value of property <FgpPropertyLink name="nodeDistributionProvided" />).
        </p>
    </FgpProperty>
</template>
