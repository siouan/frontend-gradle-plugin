<template>
    <FgpProperty
        name="nodeDistributionUrlPathPattern"
        type="java.lang.String"
        default-value="vVERSION/node-vVERSION-ARCH.TYPE"
        :task-names="['installNode']"
    >
        <p>
            This property is used to build the exact URL to download the distribution, by appending its value to the
            value of the
            <FgpPropertyLink name="nodeDistributionUrlRoot" /> property. This property may be set with a fixed trailing
            path part (e.g. <FgpCode>dist/node-v20.14.0-win-x64.zip</FgpCode>), or take advantage of the automatic
            distribution resolution in the plugin, using specific tokens in the pattern such as
            <FgpCode>VERSION</FgpCode>, <FgpCode>ARCH</FgpCode>, <FgpCode>TYPE</FgpCode>. Then, the exact URL used by
            default to download the distribution is
            <FgpCode>https://nodejs.org/dist/vVERSION/node-vVERSION-ARCH.TYPE</FgpCode>.
        </p>
        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title">About URL pattern tokens</h5>
                <p class="card-text">
                    The <FgpPropertyLink name="nodeDistributionUrlRoot" /> property and this property offer a convenient
                    way to download distributions from a custom website - e.g. a website mirroring the official
                    <FgpNodejsLink /> website, and still take advantage of the automatic resolution of the exact URL,
                    based on the current platform's architecture. The property may contain the following tokens:
                </p>
                <ul class="card-text">
                    <li>
                        <FgpCode>VERSION</FgpCode>: automatically replaced with the version number set in the
                        <FgpPropertyLink name="nodeVersion" /> property.
                    </li>
                    <li>
                        <FgpCode>ARCH</FgpCode>: automatically replaced with the architecture ID matching the current
                        platform. For example, if the current O/S is MacOS 64 bits, the plugin replaces the token with
                        <FgpCode>darwin-x64</FgpCode>.
                    </li>
                    <li>
                        <FgpCode>TYPE</FgpCode>: automatically replaced with the type of the distribution file expected
                        for the current platform. For example, if the current O/S is Windows, the plugin replaces the
                        token with <FgpCode>zip</FgpCode>.
                    </li>
                </ul>
            </div>
        </div>
    </FgpProperty>
</template>
