<template>
    <FgpProperty
        name="corepackVersion"
        type="java.lang.String"
        :required="false"
        :default-value="null"
        example="0.28.2"
        :task-names="['installCorepack']"
    >
        <p>
            When <FgpCode>null</FgpCode>, the <FgpNodejsLink /> distribution is not touched and the plugin relies on the
            default version of <FgpCorepackLink /> embedded in the distribution. Setting this property to a non-<FgpCode
            >null</FgpCode> value should not be necessary most of the time, since Corepack is expected to be more and
            more stable. But situations may exist where Corepack is not able to download the package manager correctly,
            and upgrading the default version embedded is the only way-out. There are 2 options when setting this
            property:
        </p>
        <ul>
            <li>Set a specific version with a value such as <FgpCode>X.Y.Z</FgpCode>.</li>
            <li>
                Set the latest version with the <FgpCode>latest</FgpCode> value. Note that this will not update Corepack
                automatically if a newer version is released after the <FgpTaskLink name="installCorepack" /> task has
                run.
            </li>
        </ul>
    </FgpProperty>
</template>
