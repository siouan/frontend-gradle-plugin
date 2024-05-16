<template>
    <FgpProperty
        name="cleanScript"
        type="java.lang.String"
        :required="false"
        example="run clean"
        :task-names="['cleanFrontend']"
    >
        <p>
            This property may be used to clean frontend's compiled artifacts, when they are generated out of the
            <FgpCode>${project.buildDir}</FgpCode> directory.
        </p>
        <FgpInfo>
            Take a look at this
            <FgpSiteLink path="#script-settings">guide</FgpSiteLink> about script settings.
        </FgpInfo>
    </FgpProperty>
</template>
