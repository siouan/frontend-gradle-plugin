<template>
    <FgpProperty
        name="publishScript"
        type="java.lang.String"
        :required="false"
        example="run publish"
        :task-names="['publishFrontend']"
    >
        <p>Script called to publish the frontend artifacts.</p>
        <FgpInfo>
            Take a look at this
            <FgpSiteLink path="#script-settings">guide</FgpSiteLink> about script settings.
        </FgpInfo>
    </FgpProperty>
</template>
