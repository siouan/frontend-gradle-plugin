<template>
    <FgpProperty
        name="yarnVersion"
        type="java.lang.String"
        :required="false"
        example="1.22.10"
        :task-names="['installYarn']"
    >
        <p>
            Version of <FgpYarnLink :version="1" /> that will be installed. The property is required when
            <FgpPropertyLink name="yarnEnabled" /> property is <FgpCode>true</FgpCode>.
        </p>
    </FgpProperty>
</template>
