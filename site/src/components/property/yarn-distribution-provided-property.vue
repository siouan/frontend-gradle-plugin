<template>
    <FgpProperty name="yarnDistributionProvided" type="boolean" default-value="false" :task-names="['installYarn']">
        <p>Whether the <FgpYarnLink /> distribution is already provided, and shall not be downloaded.</p>
        <ol>
            <li>
                When <FgpCode>false</FgpCode>, at least the <FgpPropertyLink name="yarnVersion" /> property must be set.
            </li>
            <li>
                When <FgpCode>true</FgpCode>, the plugin relies on the following locations in this exact order to find
                <FgpCode>yarn</FgpCode> executable:
                <ol>
                    <li>
                        The directory pointed by the
                        <FgpPropertyLink name="yarnInstallDirectory" /> property, if set.
                    </li>
                    <li>
                        The directory pointed by the
                        <FgpCode>YARN_HOME</FgpCode> environment variable, if set.
                    </li>
                    <li>Any directory in the <FgpCode>PATH</FgpCode> environment variable.</li>
                </ol>
                Other <FgpCode>yarn*</FgpCode> properties should not be used for clarity.
            </li>
        </ol>
    </FgpProperty>
</template>
