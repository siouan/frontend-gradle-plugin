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
            the value of property <FgpPropertyLink name="nodeDistributionProvided" />). Despite its default
            <FgpCode>null</FgpCode> value, when property <FgpPropertyLink name="nodeDistributionProvided" /> is
            <FgpCode>false</FgpCode> or the <FgpCode>FGP_NODEJS_HOME</FgpCode> environment variable is undefined, the
            distribution is installed or expected to be found in directory <FgpCode>${projectDir}/node</FgpCode>.
        </p>
    </FgpProperty>
</template>
