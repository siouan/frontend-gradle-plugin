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
                When <FgpCode>false</FgpCode>, at least the <FgpPropertyLink name="nodeVersion" /> property must be set.
            </li>
            <li>
                When <FgpCode>true</FgpCode>, the plugin relies on the following locations in this exact order to find
                <FgpCode>node</FgpCode>/<FgpCode>npm</FgpCode>/<FgpCode>npx</FgpCode> executables:
                <ol class="mb-3">
                    <li>
                        The directory pointed by the <FgpPropertyLink name="nodeInstallDirectory" /> property, if set.
                    </li>
                    <li>The directory pointed by the <FgpCode>NODEJS_HOME</FgpCode> environment variable, if set.</li>
                    <li>Any directory in the <FgpCode>PATH</FgpCode> environment variable.</li>
                </ol>
                Other <FgpCode>node*</FgpCode> properties should not be used for clarity.
            </li>
        </ol>
    </FgpProperty>
</template>
