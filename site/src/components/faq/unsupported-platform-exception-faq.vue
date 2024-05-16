<template>
    <FgpFaq>
        <template #question>
            What should I do when error
            <FgpCode>UnsupportedPlatformException</FgpCode>
            occurs?
        </template>
        <template #answer>
            By default, the plugin uses an automatic resolution process to "guess" the most relevant
            <FgpNodejsLink />
            distribution applicable for your O/S and CPU architecture. Technically speaking, the plugin relies on
            <FgpJavaSystemPropertiesLink>JVM system properties</FgpJavaSystemPropertiesLink>
            named <FgpCode>os.name</FgpCode> and <FgpCode>os.arch</FgpCode>. If the plugin gets values it does not
            expect from these system properties, the following error shall occur during the build:

            <FgpBlockQuote>
                Execution failed for task ':installNode'. >
                org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException: This platform is not supported yet:
                Platform(jvmArch=&lt;os.arch&gt;, osName=&lt;os.name&gt;)
            </FgpBlockQuote>

            In this case, please open an issue in the project's
            <FgpRepoLink>issue tracker</FgpRepoLink>, and provide the entire error message above. If possible, provide
            also output of command <FgpCode>java -XshowSettings:properties -version</FgpCode> and output of command
            <FgpCode>uname</FgpCode> for Unix-like O/S (beware of hiding sensitive data in both cases). As a workaround,
            consider using property <FgpPropertyLink name="nodeDistributionUrlPathPattern" /> and specify a hard-coded
            path to download the relevant distribution from the <FgpNodejsLink /> distribution server.
        </template>
    </FgpFaq>
</template>
