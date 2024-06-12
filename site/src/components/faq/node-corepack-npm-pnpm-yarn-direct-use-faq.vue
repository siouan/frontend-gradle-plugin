<template>
    <FgpFaq>
        <template #question>
            How to use
            <FgpCode>node</FgpCode>/<FgpCode>corepack</FgpCode>/<FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode
                >yarn</FgpCode
            >
            executables apart from Gradle when the <FgpNodejsLink />
            distribution is downloaded by the plugin?
        </template>
        <template #answer>
            <ul>
                <li>
                    Create a <FgpCode>NODEJS_HOME</FgpCode> environment variable (or a name of your choice) containing
                    the real path set in the <FgpPropertyLink name="nodeInstallDirectory" /> property.
                </li>
                <li>
                    Add the directory containing executables to the
                    <FgpCode>PATH</FgpCode> environment variable:
                    <ul>
                        <li>
                            On Unix-like O/S, add the
                            <FgpCode>$NODEJS_HOME/bin</FgpCode> path.
                        </li>
                        <li>On Windows O/S, add <FgpCode>%NODEJS_HOME%</FgpCode> path.</li>
                    </ul>
                </li>
            </ul>
        </template>
    </FgpFaq>
</template>
