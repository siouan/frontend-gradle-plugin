<template>
    <svg viewBox="0 0 640 440" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
        <defs>
            <g id="gradleTaskContainer">
                <rect width="120" height="30" fill="chartreuse" stroke="black" rx="3" ry="3" />
            </g>
            <g id="frontendTaskContainer">
                <rect width="120" height="30" fill="cyan" stroke="black" rx="3" ry="3" />
            </g>
            <g id="frontendTaskTypeContainer">
                <rect width="100" height="30" fill="gainsboro" stroke="black" rx="3" ry="3" />
            </g>

            <g id="cleanTaskShape">
                <use xlink:href="#gradleTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">clean</text>
            </g>
            <g id="checkTaskShape">
                <use xlink:href="#gradleTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">check</text>
            </g>
            <g id="assembleTaskShape">
                <use xlink:href="#gradleTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">assemble</text>
            </g>
            <g id="publishTaskShape">
                <use xlink:href="#gradleTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">publish</text>
            </g>
            <g id="installNodeTaskShape">
                <use xlink:href="#frontendTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    installNode
                </text>
            </g>
            <g id="installCorepackTaskShape">
                <use xlink:href="#frontendTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    installCorepack
                </text>
            </g>
            <g id="resolvePackageManagerTaskShape">
                <use xlink:href="#frontendTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    resolvePackageManager
                </text>
            </g>
            <g id="installPackageManagerTaskShape">
                <use xlink:href="#frontendTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    installPackageManager
                </text>
            </g>
            <g id="installFrontendTaskShape">
                <use xlink:href="#frontendTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    installFrontend
                </text>
            </g>
            <g id="cleanFrontendTaskShape">
                <use xlink:href="#frontendTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    cleanFrontend
                </text>
            </g>
            <g id="checkFrontendTaskShape">
                <use xlink:href="#frontendTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    checkFrontend
                </text>
            </g>
            <g id="assembleFrontendTaskShape">
                <use xlink:href="#frontendTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    assembleFrontend
                </text>
            </g>
            <g id="publishFrontendTaskShape">
                <use xlink:href="#frontendTaskContainer" />
                <text x="60" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    publishFrontend
                </text>
            </g>

            <g id="RunNodeTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">RunNodeTaskType</text>
            </g>
            <g id="RunCorepackTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">
                    RunCorepackTaskType
                </text>
            </g>
            <g id="RunNpmTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">RunNpmTaskType</text>
            </g>
            <g id="RunPnpmTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">RunPnpmTaskType</text>
            </g>
            <g id="RunYarnTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="16" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">RunYarnTaskType</text>
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
                    <use x="20" y="30" xlink:href="#cleanTaskShape" />
                </FgpGradleDocsLink>
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks">
                    <use x="170" y="30" xlink:href="#checkTaskShape" />
                </FgpGradleDocsLink>
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks">
                    <use x="320" y="30" xlink:href="#assembleTaskShape" />
                </FgpGradleDocsLink>
            </g>

            <g id="gradlePublishingPlugin">
                <rect x="480" y="0" width="160" height="70" fill="none" stroke="grey" />
                <text text-anchor="end" x="620" y="18" font-size="0.5rem" fill="grey">Gradle Publishing plugin</text>
                <use x="500" y="30" xlink:href="#publishTaskShape" />
            </g>

            <g id="frontendGradlePlugin">
                <rect x="0" y="80" width="640" height="360" fill="none" stroke="grey" />
                <text text-anchor="end" x="620" y="238" font-size="0.5rem" fill="grey">Frontend Gradle plugin</text>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#cleanFrontend`">
                    <use x="20" y="90" xlink:href="#cleanFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#checkFrontend`">
                    <use x="170" y="90" xlink:href="#checkFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#assembleFrontend`">
                    <use x="320" y="90" xlink:href="#assembleFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#publishFrontend`">
                    <use x="500" y="90" xlink:href="#publishFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installFrontend`">
                    <use x="170" y="150" xlink:href="#installFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installPackageManager`">
                    <use x="170" y="210" xlink:href="#installPackageManagerTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installCorepack`">
                    <use x="20" y="270" xlink:href="#installCorepackTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#resolvePackageManager`">
                    <use x="320" y="270" xlink:href="#resolvePackageManagerTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installNode`">
                    <use x="170" y="330" xlink:href="#installNodeTaskShape" />
                </FgpSiteLink>

                <line x1="0" y1="370" x2="640" y2="370" stroke="gainsboro" stroke-dasharray="4 2" />
                <text text-anchor="middle" x="320" y="388" font-size="0.5rem" fill="darkgrey">
                    Additional task types
                </text>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunNodeTaskType`">
                    <use x="10" y="400" xlink:href="#RunNodeTaskTypeShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunCorepackTaskType`">
                    <use x="140" y="400" xlink:href="#RunCorepackTaskTypeShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunNpmTaskType`">
                    <use x="270" y="400" xlink:href="#RunNpmTaskTypeShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunPnpmTaskType`">
                    <use x="400" y="400" xlink:href="#RunPnpmTaskTypeShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunYarnTaskType`">
                    <use x="530" y="400" xlink:href="#RunYarnTaskTypeShape" />
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

        <line x1="500" y1="105" x2="443" y2="105" stroke="violet" marker-end="url(#arrow)" />

        <path d="M 80 120 Q 80 133, 140 133 T 200 147" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <line x1="230" y1="120" x2="230" y2="147" stroke="violet" marker-end="url(#arrow)" />
        <path d="M 380 120 Q 380 133, 320 133 T 260 147" stroke="violet" fill="transparent" marker-end="url(#arrow)" />

        <line x1="230" y1="180" x2="230" y2="207" stroke="violet" marker-end="url(#arrow)" />

        <path d="M 200 240 Q 200 253, 140 253 T 80 267" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <path d="M 260 240 Q 260 253, 320 253 T 380 267" stroke="violet" fill="transparent" marker-end="url(#arrow)" />

        <path d="M 80 300 Q 80 313, 140 313 T 200 327" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <path d="M 380 300 Q 380 313, 320 313 T 260 327" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
    </svg>
</template>
