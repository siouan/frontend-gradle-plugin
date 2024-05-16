<template>
    <FgpProperty
        name="cacheDirectory"
        type="java.io.File"
        required
        default-value='project.layout.projectDirectory.dir(&quot;.frontend-gradle-plugin&quot;)'
        :task-names="['resolvePackageManager', 'installPackageManager']"
    >
        <p>
            Directory where the plugin writes some task outputs as files. This is the only way to get Gradle perform
            up-to-date checks, and reuse outputs without re-executing the related task (see also
            <FgpSiteLink path="/faqs#cache-directory">this FAQ</FgpSiteLink>).
        </p>
    </FgpProperty>
</template>
