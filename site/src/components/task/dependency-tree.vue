<template>
    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 720 460">
        <defs>
            <g id="gradleTaskContainer">
                <rect width="100" height="30" fill="#00ff00" stroke="#000000" rx="3" ry="3" />
            </g>
            <g id="frontendCommonTaskContainer">
                <rect width="100" height="30" fill="cyan" stroke="#000000" rx="3" ry="3" />
            </g>
            <g id="frontendInstantTaskContainer">
                <rect width="100" height="30" fill="cyan" stroke="#000000" rx="3" ry="3" />
            </g>
            <g id="frontendTaskTypeContainer">
                <rect width="100" height="30" fill="cyan" stroke="#000000" rx="3" ry="3" />
            </g>

            <g id="cleanTaskShape">
                <use xlink:href="#gradleTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">clean</text>
            </g>
            <g id="checkTaskShape">
                <use xlink:href="#gradleTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">check</text>
            </g>
            <g id="assembleTaskShape">
                <use xlink:href="#gradleTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">assemble</text>
            </g>
            <g id="publishTaskShape">
                <use xlink:href="#gradleTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">publish</text>
            </g>
            <g id="installNodeTaskShape">
                <use xlink:href="#frontendCommonTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">installNode</text>
            </g>
            <g id="installCorepackTaskShape">
                <use xlink:href="#frontendCommonTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">installCorepack</text>
            </g>
            <g id="resolvePackageManagerTaskShape">
                <use xlink:href="#frontendCommonTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">resolvePackageManager</text>
            </g>
            <g id="installPackageManagerTaskShape">
                <use xlink:href="#frontendCommonTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">installPackageManager</text>
            </g>
            <g id="installFrontendTaskShape">
                <use xlink:href="#frontendCommonTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">installFrontend</text>
            </g>
            <g id="cleanFrontendTaskShape">
                <use xlink:href="#frontendCommonTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">cleanFrontend</text>
            </g>
            <g id="checkFrontendTaskShape">
                <use xlink:href="#frontendCommonTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">checkFrontend</text>
            </g>
            <g id="assembleFrontendTaskShape">
                <use xlink:href="#frontendCommonTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">assembleFrontend</text>
            </g>
            <g id="publishFrontendTaskShape">
                <use xlink:href="#frontendCommonTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">publishFrontend</text>
            </g>
            <g id="runNodeTaskShape">
                <use xlink:href="#frontendInstantTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">runNode</text>
            </g>
            <g id="runCorepackTaskShape">
                <use xlink:href="#frontendInstantTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">runCorepack</text>
            </g>
            <g id="runNpmTaskShape">
                <use xlink:href="#frontendInstantTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">runNpm</text>
            </g>
            <g id="runPnpmTaskShape">
                <use xlink:href="#frontendInstantTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">runPnpm</text>
            </g>
            <g id="runYarnTaskShape">
                <use xlink:href="#frontendInstantTaskContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">runYarn</text>
            </g>

            <g id="RunNodeTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">RunNodeTaskType</text>
            </g>
            <g id="RunCorepackTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">RunCorepackTaskType</text>
            </g>
            <g id="RunNpmTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">RunNpmTaskType</text>
            </g>
            <g id="RunPnpmTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">RunPnpmTaskType</text>
            </g>
            <g id="RunYarnTaskTypeShape">
                <use xlink:href="#frontendTaskTypeContainer" />
                <text x="50" y="15" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem">RunYarnTaskType</text>
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

            <g id="gradlePlugins">
                <rect x="0" y="0" width="480" height="70" fill="none" stroke="grey" rx="10" />
                <text x="10" y="18" dominant-baseline="middle" text-anchor="begin" font-size="0.5rem" fill="green" class="text-uppercase">Gradle Base plugin</text>
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks" class="text-decoration-none">
                    <use x="10" y="30" xlink:href="#cleanTaskShape" />
                </FgpGradleDocsLink>
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks" class="text-decoration-none">
                    <use x="130" y="30" xlink:href="#checkTaskShape" />
                </FgpGradleDocsLink>
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks" class="text-decoration-none">
                    <use x="250" y="30" xlink:href="#assembleTaskShape" />
                </FgpGradleDocsLink>
                <line x1="360" y1="0" x2="360" y2="70" stroke="gainsboro" stroke-dasharray="4 2" />
                <text x="370" y="18" dominant-baseline="middle" text-anchor="begin" font-size="0.5rem" fill="green" class="text-uppercase">Gradle Publishing plugin</text>
                <use x="370" y="30" xlink:href="#publishTaskShape" />
            </g>

            <g id="frontendGradlePlugin">
                <rect x="0" y="90" width="720" height="370" fill="none" stroke="grey" rx="10" />
                <text x="10" y="235" dominant-baseline="middle" text-anchor="begin" font-size="0.5rem" fill="green" class="text-uppercase">Frontend Gradle plugin</text>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#cleanFrontend`" class="text-decoration-none">
                    <use x="10" y="100" xlink:href="#cleanFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#checkFrontend`" class="text-decoration-none">
                    <use x="130" y="100" xlink:href="#checkFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#assembleFrontend`" class="text-decoration-none">
                    <use x="250" y="100" xlink:href="#assembleFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#publishFrontend`" class="text-decoration-none">
                    <use x="370" y="100" xlink:href="#publishFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installFrontend`" class="text-decoration-none">
                    <use x="130" y="180" xlink:href="#installFrontendTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installPackageManager`" class="text-decoration-none">
                    <use x="130" y="260" xlink:href="#installPackageManagerTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#resolvePackageManager`" class="text-decoration-none">
                    <use x="10" y="340" xlink:href="#resolvePackageManagerTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installCorepack`" class="text-decoration-none">
                    <use x="250" y="340" xlink:href="#installCorepackTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#installNode`" class="text-decoration-none">
                    <use x="130" y="420" xlink:href="#installNodeTaskShape" />
                </FgpSiteLink>
                <text x="470" y="195" dominant-baseline="middle" text-anchor="end" font-size="0.5rem" fill="darkgrey">Lifecycle tasks</text>

                <line x1="480" y1="90" x2="480" y2="460" stroke="gainsboro" stroke-dasharray="4 2" />

                <FgpSiteLink :path="`${$config.public.paths.tasks}#runNpm`" class="text-decoration-none">
                    <use x="490" y="220" xlink:href="#runNpmTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#runPnpm`" class="text-decoration-none">
                    <use x="490" y="260" xlink:href="#runPnpmTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#runYarn`" class="text-decoration-none">
                    <use x="490" y="300" xlink:href="#runYarnTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#runCorepack`" class="text-decoration-none">
                    <use x="490" y="340" xlink:href="#runCorepackTaskShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#runNode`" class="text-decoration-none">
                    <use x="490" y="420" xlink:href="#runNodeTaskShape" />
                </FgpSiteLink>
                <text x="540" y="195" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem" fill="darkgrey">Instant tasks</text>

                <line x1="600" y1="90" x2="600" y2="460" stroke="gainsboro" stroke-dasharray="4 2" />

                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunNpmTaskType`" class="text-decoration-none">
                    <use x="610" y="220" xlink:href="#RunNpmTaskTypeShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunPnpmTaskType`" class="text-decoration-none">
                    <use x="610" y="260" xlink:href="#RunPnpmTaskTypeShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunYarnTaskType`" class="text-decoration-none">
                    <use x="610" y="300" xlink:href="#RunYarnTaskTypeShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunCorepackTaskType`" class="text-decoration-none">
                    <use x="610" y="340" xlink:href="#RunCorepackTaskTypeShape" />
                </FgpSiteLink>
                <FgpSiteLink :path="`${$config.public.paths.tasks}#RunNodeTaskType`" class="text-decoration-none">
                    <use x="610" y="420" xlink:href="#RunNodeTaskTypeShape" />
                </FgpSiteLink>
                <text x="660" y="195" dominant-baseline="middle" text-anchor="middle" font-size="0.5rem" fill="darkgrey">Task types</text>
            </g>
        </defs>

        <use xlink:href="#gradlePlugins" />
        <use xlink:href="#frontendGradlePlugin" />

        <line x1="60" y1="60" x2="60" y2="97" stroke="violet" marker-end="url(#arrow)" />
        <line x1="180" y1="60" x2="180" y2="97" stroke="violet" marker-end="url(#arrow)" />
        <line x1="300" y1="60" x2="300" y2="97" stroke="violet" marker-end="url(#arrow)" />
        <line x1="420" y1="60" x2="420" y2="97" stroke="violet" marker-end="url(#arrow)" />

        <line x1="370" y1="115" x2="353" y2="115" stroke="violet" marker-end="url(#arrow)" />

        <path d="M 60 130 Q 60 153, 110 153 T 160 177" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <line x1="180" y1="130" x2="180" y2="177" stroke="violet" marker-end="url(#arrow)" />
        <path d="M 300 130 Q 300 153, 250 153 T 200 177" stroke="violet" fill="transparent" marker-end="url(#arrow)" />

        <line x1="180" y1="210" x2="180" y2="257" stroke="violet" marker-end="url(#arrow)" />

        <path d="M 160 290 Q 160 313, 110 313 T 60 337" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <path d="M 200 290 Q 200 313, 250 313 T 300 337" stroke="violet" fill="transparent" marker-end="url(#arrow)" />

        <path d="M 60 370 Q 60 393, 110 393 T 160 417" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <path d="M 300 370 Q 300 393, 250 393 T 200 417" stroke="violet" fill="transparent" marker-end="url(#arrow)" />

        <path d="M 490 235 Q 362 235, 360 255 T 233 275" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <line x1="600" y1="90" x2="600" y2="460" stroke="gainsboro" stroke-dasharray="4 2" />
        <line x1="610" y1="235" x2="590" y2="235" stroke="red" stroke-dasharray="1 1" />
        <line x1="490" y1="275" x2="233" y2="275" stroke="violet" marker-end="url(#arrow)" />
        <line x1="610" y1="275" x2="590" y2="275" stroke="red" stroke-dasharray="1 1" />
        <path d="M 490 315 Q 362 315, 360 295 T 233 275" stroke="violet" fill="transparent" marker-end="url(#arrow)" />
        <line x1="610" y1="315" x2="590" y2="315" stroke="red" stroke-dasharray="1 1" />
        <line x1="490" y1="355" x2="353" y2="355" stroke="violet" marker-end="url(#arrow)" />
        <line x1="610" y1="355" x2="590" y2="355" stroke="red" stroke-dasharray="1 1" />
        <line x1="490" y1="435" x2="233" y2="435" stroke="violet" marker-end="url(#arrow)" />
        <line x1="610" y1="435" x2="590" y2="435" stroke="red" stroke-dasharray="1 1" />
    </svg>
</template>
