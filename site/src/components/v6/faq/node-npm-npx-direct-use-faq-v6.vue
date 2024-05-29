<template>
    <FgpFaq>
        <template v-slot:question>
            How to use <FgpCode>node</FgpCode>/<FgpCode>npm</FgpCode>/<FgpCode>npx</FgpCode> executables apart
            from Gradle when the <FgpNodejsLink /> distribution is downloaded by the plugin?
        </template>
        <template v-slot:answer>
            <ul>
                <li>
                    Create a <FgpCode>NODEJS_HOME</FgpCode> environment variable containing the real path set in the
                    <FgpPropertyLink name="nodeInstallDirectory" /> property.
                </li>
                <li>
                    Add the directory containing executables to the <FgpCode>PATH</FgpCode> environment variable:
                    <ul>
                        <li>On Unix-like O/S, add the <FgpCode>$NODEJS_HOME/bin</FgpCode> path.</li>
                        <li>On Windows O/S, add <FgpCode>%NODEJS_HOME%</FgpCode> path.</li>
                    </ul>
                </li>
            </ul>
            <FgpInfo>
                Applies only when the <FgpPropertyLink name="nodeDistributionProvided" /> property is
                <FgpCode>false</FgpCode>.
            </FgpInfo>
        </template>
    </FgpFaq>
</template>
