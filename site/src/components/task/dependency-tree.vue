<template>
    <svg viewBox="0 0 640 440" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
        <defs>
            <g id="gradleTask">
                <rect width="120" height="30" fill="chartreuse" stroke="black" rx="3" ry="3" />
            </g>
            <g id="frontendTask">
                <rect width="120" height="30" fill="cyan" stroke="black" rx="3" ry="3" />
            </g>
            <g id="frontendTaskType">
                <rect width="100" height="30" fill="gainsboro" stroke="black" rx="3" ry="3" />
            </g>

            <g id="cleanTask">
                <use xlink:href="#gradleTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">clean</text>
            </g>
            <g id="checkTask">
                <use xlink:href="#gradleTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">check</text>
            </g>
            <g id="assembleTask">
                <use xlink:href="#gradleTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">assemble</text>
            </g>
            <g id="publishTask">
                <use xlink:href="#gradleTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">publish</text>
            </g>
            <g id="installNodeTask">
                <use xlink:href="#frontendTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">
                    installNode
                </text>
            </g>
            <g id="resolvePackageManagerTask">
                <use xlink:href="#frontendTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">
                    resolvePackageManager
                </text>
            </g>
            <g id="installPackageManagerTask">
                <use xlink:href="#frontendTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">
                    installPackageManager
                </text>
            </g>
            <g id="installFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">
                    installFrontend
                </text>
            </g>
            <g id="cleanFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">
                    cleanFrontend
                </text>
            </g>
            <g id="checkFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">
                    checkFrontend
                </text>
            </g>
            <g id="assembleFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">
                    assembleFrontend
                </text>
            </g>
            <g id="publishFrontendTask">
                <use xlink:href="#frontendTask" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">
                    publishFrontend
                </text>
            </g>

            <g id="RunNodeTaskType">
                <use xlink:href="#frontendTaskType" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">RunNode</text>
            </g>
            <g id="RunCorepackTaskType">
                <use xlink:href="#frontendTaskType" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">
                    RunCorepack
                </text>
            </g>
            <g id="RunNpmTaskType">
                <use xlink:href="#frontendTaskType" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">RunNpm</text>
            </g>
            <g id="RunPnpmTaskType">
                <use xlink:href="#frontendTaskType" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">RunPnpm</text>
            </g>
            <g id="RunYarnTaskType">
                <use xlink:href="#frontendTaskType" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.6rem">RunYarn</text>
            </g>

            <marker
                id="arrow"
                viewBox="0 0 10 10"
                refX="5"
                refY="5"
                markerWidth="6"
                markerHeight="6"
                orient="auto-start-reverse"
                fill="violet"
            >
                <path d="M 0 0 L 10 5 L 0 10 z" />
            </marker>

            <g id="gradleBasePlugin">
                <rect x="0" y="0" width="460" height="70" fill="none" stroke="grey" />
                <text x="20" y="18" font-size="0.5rem" fill="grey">Gradle Base plugin</text>
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks">
                    <use x="20" y="30" xlink:href="#cleanTask" />
                </FgpGradleDocsLink>
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks">
                    <use x="170" y="30" xlink:href="#checkTask" />
                </FgpGradleDocsLink>
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks">
                    <use x="320" y="30" xlink:href="#assembleTask" />
                </FgpGradleDocsLink>
            </g>

            <g id="gradlePublishingPlugin">
                <rect x="480" y="0" width="160" height="70" fill="none" stroke="grey" />
                <text text-anchor="end" x="620" y="18" font-size="0.5rem" fill="grey">Gradle Publishing plugin</text>
                <use x="500" y="30" xlink:href="#publishTask" />
            </g>

            <g id="frontendGradlePlugin">
                <rect x="0" y="80" width="640" height="360" fill="none" stroke="grey" />
                <text text-anchor="end" x="620" y="238" font-size="0.5rem" fill="grey">Frontend Gradle plugin</text>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#cleanFrontend`">
                    <use x="20" y="90" xlink:href="#cleanFrontendTask" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#checkFrontend`">
                    <use x="170" y="90" xlink:href="#checkFrontendTask" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#assembleFrontend`">
                    <use x="320" y="90" xlink:href="#assembleFrontendTask" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#publishFrontend`">
                    <use x="500" y="90" xlink:href="#publishFrontendTask" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installFrontend`">
                    <use x="170" y="150" xlink:href="#installFrontendTask" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installPackageManager`">
                    <use x="170" y="210" xlink:href="#installPackageManagerTask" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#resolvePackageManager`">
                    <use x="170" y="270" xlink:href="#resolvePackageManagerTask" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installNode`">
                    <use x="170" y="330" xlink:href="#installNodeTask" />
                </FgpSiteLink>

                <line x1="0" y1="370" x2="640" y2="370" stroke="gainsboro" stroke-dasharray="4 2" />
                <text text-anchor="middle" x="320" y="388" font-size="0.5rem" fill="darkgrey">
                    Additional task types
                </text>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunNode`">
                    <use x="10" y="400" xlink:href="#RunNodeTaskType" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunCorepack`">
                    <use x="140" y="400" xlink:href="#RunCorepackTaskType" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunNpm`">
                    <use x="270" y="400" xlink:href="#RunNpmTaskType" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunPnpm`">
                    <use x="400" y="400" xlink:href="#RunPnpmTaskType" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunYarn`">
                    <use x="530" y="400" xlink:href="#RunYarnTaskType" />
                </FgpSiteLink>
            </g>
        </defs>

        <use xlink:href="#gradleBasePlugin" />
        <use xlink:href="#gradlePublishingPlugin" />
        <use xlink:href="#frontendGradlePlugin" />

        <line x1="80" y1="60" x2="80" y2="87" stroke="violet" marker-end="url(#arrow)" />
        <line x1="230" y1="60" x2="230" y2="87" stroke="violet" marker-end="url(#arrow)" />
        <line x1="380" y1="60" x2="380" y2="87" stroke="violet" marker-end="url(#arrow)" />
        <line x1="560" y1="60" x2="560" y2="87" stroke="violet" marker-end="url(#arrow)" />

        <path d="M 80 120 Q 80 133, 140 133 T 200 147" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <line x1="230" y1="120" x2="230" y2="147" stroke="violet" marker-end="url(#arrow)" />
        <path d="M 380 120 Q 380 133, 320 133 T 260 147" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <line x1="500" y1="105" x2="443" y2="105" stroke="violet" marker-end="url(#arrow)" />

        <line x1="230" y1="180" x2="230" y2="207" stroke="violet" marker-end="url(#arrow)" />
        <line x1="230" y1="240" x2="230" y2="267" stroke="violet" marker-end="url(#arrow)" />
        <line x1="230" y1="300" x2="230" y2="327" stroke="violet" marker-end="url(#arrow)" />
    </svg>
</template>
