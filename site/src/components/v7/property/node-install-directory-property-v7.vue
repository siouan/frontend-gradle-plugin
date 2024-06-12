<template>
    <FgpProperty
        name="nodeInstallDirectory"
        type="java.io.File"
        :required="false"
        default-value="null"
        :task-names="[
            'installNode',
            'resolvePackageManager',
            'installPackageManager',
            'installFrontend',
            'cleanFrontend',
            'assembleFrontend',
            'checkFrontend',
            'publishFrontend'
        ]"
    >
        <p>
            When property <FgpPropertyLink name="nodeDistributionProvided" /> is <FgpCode>false</FgpCode>, the
            downloaded distribution is installed in the directory pointed by this property (defaults to
            <FgpCode>file("${projectDir}/node")</FgpCode>). When property
            <FgpPropertyLink name="nodeDistributionProvided" /> is <FgpCode>true</FgpCode>, this property may be
            used to point the directory where the distribution is already installed. If <FgpCode>null</FgpCode>, the
            plugin tries to find the installation directory with the <FgpCode>FGP_NODEJS_HOME</FgpCode> environment
            variable.
        </p>
    </FgpProperty>
</template>
