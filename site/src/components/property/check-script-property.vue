<template>
    <FgpProperty
        name="checkScript"
        type="java.lang.String"
        :required="false"
        example="run check"
        :task-names="['checkFrontend']"
    >
        <p>Script called to check the frontend application.</p>
        <FgpInfo>
            Take a look at this
            <FgpSiteLink path="#script-settings">guide</FgpSiteLink> about script settings.
        </FgpInfo>
    </FgpProperty>
</template>
