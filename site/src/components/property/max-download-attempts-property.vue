<template>
    <FgpProperty
        name="maxDownloadAttempts"
        type="java.lang.Integer"
        :required="true"
        default-value="1"
        :task-names="['installNode']"
    >
        <p>
            Based on this property, the plugin may retry to download the
            <FgpNodejsLink /> distribution in case of error. A value of <FgpCode>1</FgpCode> indicates the plugin will
            not retry download (one attempt only). This property applies to the distribution file download as well as
            its shasum file download (see task <FgpTaskLink name="installNode" />) used to validate the distribution
            file's integrity. Connectivity errors (distribution server reachability, network failures) and
            <FgpPropertyLink name="retryHttpStatuses">some HTTP errors</FgpPropertyLink>
            trigger a retry of the download if more attempts are permitted thanks to this property. The retry strategy
            is based on the exponential backoff algorithm to determine the waiting time before the next download
            attempt:
            <math display="block" class="mt-1 text-monospace">
                <mrow>
                    <mi><FgpCode>waiting time (ms)</FgpCode></mi>
                    <mo>=</mo>
                    <mi class="text-info">min</mi>
                    <mo fence="true">(</mo>
                    <mi><FgpPropertyLink name="retryInitialIntervalMs" /></mi>
                    <mo class="text-danger">&times;</mo>
                    <msup>
                        <mi class="text-primary"><FgpPropertyLink name="retryIntervalMultiplier" /></mi>
                        <mrow>
                            <mo fence="true">(</mo>
                            <mi>attempted downloads</mi>
                            <mo class="text-danger">&minus;</mo>
                            <mn>1</mn>
                            <mo fence="true">)</mo>
                        </mrow>
                    </msup>
                    <mo separator="true">,</mo>
                    <mi><FgpPropertyLink name="retryMaxIntervalMs" /></mi>
                    <mo fence="true">)</mo>
                </mrow>
            </math>
        </p>
    </FgpProperty>
</template>
