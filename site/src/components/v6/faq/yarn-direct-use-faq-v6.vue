<template>
    <FgpFaq>
        <template v-slot:question>
            How to use <FgpCode>yarn</FgpCode> executable apart from Gradle when the <FgpYarnLink /> distribution is
            downloaded by the plugin?
        </template>
        <template v-slot:answer>
            The following answer applies only when the <FgpPropertyLink name="yarnDistributionProvided" /> property
            is <FgpCode>false</FgpCode>.
            <ul>
                <li>
                    Create a <FgpCode>YARN_HOME</FgpCode> environment variable containing the real path set in the
                    <FgpPropertyLink name="yarnInstallDirectory" /> property.
                </li>
                <li>
                    Add the directory containing executables to the <FgpCode>PATH</FgpCode> environment variable:
                    <ul>
                        <li>On Unix-like O/S, add the <FgpCode>$YARN_HOME/bin</FgpCode> path.</li>
                        <li>On Windows O/S, add <FgpCode>%YARN_HOME%\bin</FgpCode> path.</li>
                    </ul>
                </li>
            </ul>
        </template>
    </FgpFaq>
</template>
