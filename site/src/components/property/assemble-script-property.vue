<template>
    <FgpProperty
        name="assembleScript"
        type="java.lang.String"
        :required="false"
        example="run assemble"
        :task-names="['assembleFrontend', 'publishFrontend']"
    >
        <p>Script called to build frontend artifacts.</p>
        <FgpInfo>
            Take a look at this
            <FgpSiteLink path="#script-settings">guide</FgpSiteLink> about script settings.
        </FgpInfo>
    </FgpProperty>
</template>
