<template>
    <FgpProperty
        name="nodeDistributionProvided"
        type="boolean"
        default-value="false"
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
        <p>Whether the <FgpNodejsLink /> distribution is already provided, and shall not be downloaded.</p>
        <ol>
            <li>
                When <FgpCode>false</FgpCode>, task <FgpTaskLink name="installNode" /> downloads a
                <FgpNodejsLink /> distribution using properties <FgpPropertyLink name="nodeVersion" />,
                <FgpPropertyLink name="nodeDistributionUrlRoot" />,
                <FgpPropertyLink name="nodeDistributionUrlPathPattern" />, and installs it in the directory pointed by
                the <FgpPropertyLink name="nodeInstallDirectory" /> property.
            </li>
            <li>
                <p>
                    When <FgpCode>true</FgpCode>, the plugin relies on the directory pointed by the
                    <FgpPropertyLink name="nodeInstallDirectory" /> property to locate the
                    <FgpNodejsLink /> distribution. Other <FgpCode>node*</FgpCode> properties should not be used for
                    clarity.
                </p>

                <FgpWarning
                    >CAUTION: globally installed distribution is modified when using the plugin and <FgpCorepackLink />.
                    Executables <FgpCode>npm</FgpCode>, <FgpCode>pnpm</FgpCode>, <FgpCode>yarn</FgpCode> that may
                    already exists within <FgpNodejsLink /> install directory are overwritten when task
                    <FgpTaskLink name="installPackageManager" /> is run.</FgpWarning
                >
            </li>
        </ol>
    </FgpProperty>
</template>
