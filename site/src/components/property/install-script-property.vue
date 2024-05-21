<template>
    <FgpProperty
        name="installScript"
        type="java.lang.String"
        default-value="install"
        example="ci"
        :task-names="['installFrontend']"
    >
        <p>This property may be used to customize the command line to install frontend dependencies.</p>
        <FgpInfo>
            Take a look at this
            <FgpSiteLink :path="`${$config.public.paths.configuration}#script-settings`">guide</FgpSiteLink> about script settings.
        </FgpInfo>
    </FgpProperty>
</template>
