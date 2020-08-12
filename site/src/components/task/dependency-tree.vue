<template>
    <svg viewBox="0 0 510 320" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
        <defs>
            <g id="gradleTask">
                <rect width="100" height="30" fill="chartreuse" stroke="black" rx="3" ry="3" />
            </g>
            <g id="frontendTask">
                <rect width="100" height="30" fill="cyan" stroke="black" rx="3" ry="3" />
            </g>
            <g id="frontendTaskType">
                <rect width="100" height="30" fill="gainsboro" stroke="black" rx="3" ry="3" />
            </g>

            <g id="cleanTask">
                <use xlink:href="#gradleTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">clean</text>
            </g>
            <g id="assembleTask">
                <use xlink:href="#gradleTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">assemble</text>
            </g>
            <g id="checkTask">
                <use xlink:href="#gradleTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">check</text>
            </g>
            <g id="publishTask">
                <use xlink:href="#gradleTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">publish</text>
            </g>
            <g id="installNodeTask">
                <use xlink:href="#frontendTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">
                    installNode
                </text>
            </g>
            <g id="installYarnTask">
                <use xlink:href="#frontendTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">
                    installYarn
                </text>
            </g>
            <g id="installFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">
                    installFrontend
                </text>
            </g>
            <g id="cleanFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">
                    cleanFrontend
                </text>
            </g>
            <g id="assembleFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">
                    assembleFrontend
                </text>
            </g>
            <g id="checkFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">
                    checkFrontend
                </text>
            </g>
            <g id="publishFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">
                    publishFrontend
                </text>
            </g>

            <g id="RunNodeTaskType">
                <use xlink:href="#frontendTaskType" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">RunNode</text>
            </g>
            <g id="RunNpmYarnTaskType">
                <use xlink:href="#frontendTaskType" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">RunNpmYarn</text>
            </g>
            <g id="RunNpxTaskType">
                <use xlink:href="#frontendTaskType" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.7rem">RunNpx</text>
            </g>

            <marker
                id="arrow"
                viewBox="0 0 10 10"
                refX="5"
                refY="5"
                markerWidth="6"
                markerHeight="6"
                orient="auto-start-reverse"
            >
                <path d="M 0 0 L 10 5 L 0 10 z" />
            </marker>

            <g id="arrow">
                <line x1="60" y1="80" x2="100" y2="20" />
            </g>

            <g id="gradleBasePlugin">
                <rect x="0" y="0" width="380" height="70" fill="none" stroke="grey" />
                <text x="10" y="18" font-size="0.5rem" fill="grey">Gradle Base plugin</text>
                <fgp-gradle-docs-link path="/current/userguide/base_plugin.html#sec:base_tasks">
                    <use x="10" y="30" xlink:href="#cleanTask" />
                </fgp-gradle-docs-link>
                <fgp-gradle-docs-link path="/current/userguide/base_plugin.html#sec:base_tasks">
                    <use x="140" y="30" xlink:href="#assembleTask" />
                </fgp-gradle-docs-link>
                <fgp-gradle-docs-link path="/current/userguide/base_plugin.html#sec:base_tasks">
                    <use x="270" y="30" xlink:href="#checkTask" />
                </fgp-gradle-docs-link>
            </g>

            <g id="gradlePublishingPlugin">
                <rect x="390" y="0" width="120" height="70" fill="none" stroke="grey" />
                <text text-anchor="end" x="500" y="18" font-size="0.5rem" fill="grey">Gradle Publishing plugin</text>
                <use x="400" y="30" xlink:href="#publishTask" />
            </g>

            <g id="frontendGradlePlugin">
                <rect x="0" y="80" width="510" height="240" fill="none" stroke="grey" />
                <text text-anchor="end" x="500" y="238" font-size="0.5rem" fill="grey">Frontend Gradle plugin</text>
                <fgp-site-link :path="`${fgp.paths.tasks}#cleanFrontend`">
                    <use x="10" y="90" xlink:href="#cleanFrontendTask" />
                </fgp-site-link>
                <fgp-site-link path="#assembleFrontend">
                    <use x="140" y="90" xlink:href="#assembleFrontendTask" />
                </fgp-site-link>
                <fgp-site-link path="#checkFrontend">
                    <use x="270" y="90" xlink:href="#checkFrontendTask" />
                </fgp-site-link>
                <fgp-site-link path="#publishFrontend">
                    <use x="400" y="90" xlink:href="#publishFrontendTask" />
                </fgp-site-link>
                <fgp-site-link path="#installFrontend">
                    <use x="140" y="150" xlink:href="#installFrontendTask" />
                </fgp-site-link>
                <fgp-site-link path="#installNode">
                    <use x="75" y="210" xlink:href="#installNodeTask" />
                </fgp-site-link>
                <fgp-site-link path="#installYarn">
                    <use x="205" y="210" xlink:href="#installYarnTask" />
                </fgp-site-link>

                <line x1="0" y1="250" x2="510" y2="250" stroke="gainsboro" stroke-dasharray="4 2" />
                <text text-anchor="middle" x="255" y="268" font-size="0.5rem" fill="darkgrey">
                    Additional task types
                </text>
                <fgp-site-link path="#RunNode">
                    <use x="75" y="280" xlink:href="#RunNodeTaskType" />
                </fgp-site-link>
                <fgp-site-link path="#RunNpmYarn">
                    <use x="205" y="280" xlink:href="#RunNpmYarnTaskType" />
                </fgp-site-link>
                <fgp-site-link path="#RunNpx">
                    <use x="335" y="280" xlink:href="#RunNpxTaskType" />
                </fgp-site-link>
            </g>
        </defs>

        <use xlink:href="#gradleBasePlugin" />
        <use xlink:href="#gradlePublishingPlugin" />
        <use xlink:href="#frontendGradlePlugin" />

        <line x1="60" y1="60" x2="60" y2="85" stroke="black" marker-end="url(#arrow)" />
        <line x1="190" y1="60" x2="190" y2="85" stroke="black" marker-end="url(#arrow)" />
        <line x1="320" y1="60" x2="320" y2="85" stroke="black" marker-end="url(#arrow)" />
        <line x1="450" y1="60" x2="450" y2="85" stroke="black" marker-end="url(#arrow)" />

        <line x1="60" y1="120" x2="175" y2="145" stroke="black" marker-end="url(#arrow)" />
        <line x1="190" y1="120" x2="190" y2="145" stroke="black" marker-end="url(#arrow)" />
        <line x1="320" y1="120" x2="205" y2="145" stroke="black" marker-end="url(#arrow)" />
        <line x1="400" y1="105" x2="375" y2="105" stroke="black" marker-end="url(#arrow)" />

        <line x1="190" y1="180" x2="125" y2="205" stroke="black" marker-end="url(#arrow)" />
        <line x1="190" y1="180" x2="255" y2="205" stroke="black" marker-end="url(#arrow)" />
    </svg>
</template>

<script>
import Vue from "vue";
import fgpSiteLink from "../link/site-link";
import fgpAppConfig from "../../mixin/app-config";

export default Vue.component("fgp-dependency-tree", {
    components: { fgpSiteLink },
    mixins: [fgpAppConfig]
});
</script>
