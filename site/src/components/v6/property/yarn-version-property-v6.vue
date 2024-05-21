<template>
    <FgpProperty
        name="yarnVersion"
        type="java.lang.String"
        :required="false"
        example="3.0.0"
        :task-names="['installYarn']"
    >
        <p>
            Version of <FgpYarnLink /> that will be installed. The property is required when
            <FgpPropertyLink name="yarnEnabled" /> property is <FgpCode>true</FgpCode>.
        </p>
    </FgpProperty>
</template>
