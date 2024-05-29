<template>
    <FgpProperty
        name="nodeDistributionProvided"
        type="boolean"
        default-value="false"
        :task-names="['installNode']"
    >
        <p>Whether the <FgpNodejsLink /> distribution is already provided, and shall not be downloaded.</p>
        <ol>
            <li>
                When <FgpCode>false</FgpCode>, task <FgpTaskLink name="installNode" /> downloads and installs a
                <FgpNodejsLink /> distribution using properties <FgpPropertyLink name="nodeVersion" />,
                <FgpPropertyLink name="nodeDistributionUrlRoot" />,
                <FgpPropertyLink name="nodeDistributionUrlPathPattern" />.
            </li>
            <li>
                When <FgpCode>true</FgpCode>, the plugin relies on the following locations in this exact order to find
                <FgpCode>node</FgpCode>/<FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode> executables:
                <ol class="mb-3">
                    <li>
                        The directory pointed by the <FgpPropertyLink name="nodeInstallDirectory" /> property, if set.
                    </li>
                    <li>
                        The directory pointed by the <FgpCode>FGP_NODEJS_HOME</FgpCode> environment variable, if set.
                    </li>
                </ol>
                <FgpWarning>CAUTION: globally installed distribution is modified when using the plugin and
                <FgpCorepackLink />. Executables <FgpCode>npm</FgpCode>, <FgpCode>pnpm</FgpCode>,
                <FgpCode>yarn</FgpCode> that may already exists within <FgpNodejsLink /> install directory are
                overwritten when task <FgpTaskLink name="installPackageManager" /> is run.</FgpWarning>
                Other <FgpCode>node*</FgpCode> properties should not be used for clarity.
            </li>
        </ol>
    </FgpProperty>
</template>
