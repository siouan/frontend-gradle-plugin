<template>
    <FgpProperty
        name="yarnDistributionUrlPathPattern"
        type="java.lang.String"
        default-value="vVERSION/yarn-vVERSION.tar.gz"
        :task-names="['installYarn']"
    >
        <p>
            This property is used to build the exact URL to download the distribution, by appending its value to the
            value of the
            <FgpPropertyLink name="yarnDistributionUrlRoot" /> property. This property may be set with a fixed trailing
            path part (e.g. <FgpCode>dist/yarn-v1.22.10.tar.gz</FgpCode>), or take advantage of the automatic
            distribution resolution in the plugin, using specific token in the pattern such as
            <FgpCode>VERSION</FgpCode> (see the <FgpTaskLink name="installYarn" /> task).
        </p>
        <p>
            Then, the exact URL used by default to download the distribution is
            <FgpCode>https://github.com/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz</FgpCode>.
        </p>
        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title">About URL pattern token</h5>
                <p class="card-text">
                    The <FgpPropertyLink name="yarnDistributionUrlRoot" /> property and this property offer a convenient
                    way to download distributions from a custom website - e.g. a website mirroring the official
                    <FgpYarnLink /> website, and still take advantage of the automatic resolution of the exact URL. The
                    property may contain the following token:
                </p>
                <ul class="card-text">
                    <li>
                        <FgpCode>VERSION</FgpCode>: automatically replaced with the version number set in the
                        <FgpPropertyLink name="yarnVersion" /> property.
                    </li>
                </ul>
            </div>
        </div>
    </FgpProperty>
</template>
