<h2 align="center">Frontend Gradle plugin - Integrated <a href="https://nodejs.org/" title="Node.js">Node.js</a>,
<a href="https://www.npmjs.com/" title="npm">npm</a>, <a href="https://pnpm.io/" title="pnpm">pnpm</a>,
<a href="https://yarnpkg.com/" title="Yarn">Yarn</a> builds</h2> 
<p align="center">
    <a href="https://github.com/siouan/frontend-gradle-plugin/releases/tag/v10.0.0"><img src="https://img.shields.io/badge/Latest%20release-10.0.0-blue.svg" alt="Latest release 10.0.0"/></a>
    <br/>
    <a href="https://github.com/siouan/frontend-gradle-plugin/actions/workflows/build.yml"><img src="https://github.com/siouan/frontend-gradle-plugin/actions/workflows/build.yml/badge.svg?branch=main" alt="Build status"/></a>
    <a href="https://sonarcloud.io/project/overview?id=siouan_frontend-gradle-plugin"><img src="https://sonarcloud.io/api/project_badges/measure?project=siouan_frontend-gradle-plugin&metric=alert_status" alt="Quality gate status"/></a>
    <a href="https://sonarcloud.io/summary/overall?id=siouan_frontend-gradle-plugin"><img src="https://sonarcloud.io/api/project_badges/measure?project=siouan_frontend-gradle-plugin&metric=coverage" alt="Code coverage"/></a>
    <a href="https://sonarcloud.io/summary/overall?id=siouan_frontend-gradle-plugin"><img src="https://sonarcloud.io/api/project_badges/measure?project=siouan_frontend-gradle-plugin&metric=reliability_rating" alt="Reliability"/></a>
</p>

<p align="center">
<a href="https://gradle.org/" title="Gradle"><img src="resources/gradle-icon.svg" alt="Gradle icon" height="64" /></a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://nodejs.org/" title="Node.js"><img src="resources/nodejs-icon.svg" alt="Node.js icon" height="64" /></a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://www.npmjs.com/" title="npm"><img src="resources/npm-icon.svg" alt="npm icon" height="64" /></a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://pnpm.io/" title="pnpm"><img src="resources/pnpm-icon.svg" alt="pnpm icon" height="64" /></a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://yarnpkg.com/" title="Yarn"><img src="resources/yarn-icon.svg" alt="Yarn icon" height="64" /></a>
</p>

## Summary

- [Official documentation][official-documentation]
- [Release notes][release-notes]
- [Examples][examples]
    - [Build a NPM application][example-npm-application]
    - [Build a PNPM application][example-pnpm-application]
    - [Build a YARN application (default pnp linker)][example-yarn-application-pnp-linker]
    - [Build a YARN application (node-modules linker)][example-yarn-application-node-modules-linker]
    - [Build an application using a preinstalled Node.js distribution][example-application-with-preinstalled-nodejs-distribution]
    - [Build multiple applications with different package managers and using a shared Node.js distribution][example-multiple-package-managers-with-shared-nodejs-distribution]
    - [Build a full-stack Spring Boot WAR application with dedicated frontend and backend subprojects][example-fullstack-war-application]
- [Special thanks](#special-thanks)
- [Contributing][contributing]

## Special thanks

### They use this plugin, thanks to these organizations!

<p align="center">
<a href="https://akhq.io/" title="AKHQ - Kafka GUI for Apache Kafka"><picture>
    <source media="(prefers-color-scheme: dark)" srcset="resources/akhq-dark.svg" />
    <source media="(prefers-color-scheme: light)" srcset="resources/akhq-light.png" />
    <img src="resources/akhq-light.png" alt="AKHQ logo" height="64" />
</picture></a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://kestra.io/" title="Kestra - Event-driven declarative orchestration platform"><picture>
    <source media="(prefers-color-scheme: dark)" srcset="resources/kestra-dark.svg" />
    <source media="(prefers-color-scheme: light)" srcset="resources/kestra-light.png" />
    <img src="resources/kestra-light.png" alt="Kestra logo" height="64" />
</picture></a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://egeria-project.org/" title="Egeria - Open Metadata and Governance"><picture>
    <source media="(prefers-color-scheme: dark)" srcset="resources/egeria-dark.png" />
    <source media="(prefers-color-scheme: light)" srcset="resources/egeria-light.png" />
    <img src="resources/egeria-light.png" alt="Egeria logo" height="64" />
</picture></a>
<br/>
<a href="https://x-road.global/" title="X-Road - Data Exchange Layer"><picture>
    <source media="(prefers-color-scheme: dark)" srcset="resources/x-road-dark.png" />
    <source media="(prefers-color-scheme: light)" srcset="resources/x-road-light.png" />
    <img src="resources/x-road-light.png" alt="X-Road logo" height="64" />
</picture></a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://serverpackcreator.de/" title="ServerPackCreator - Quick and easy generation of server packs of your favourite Minecraft modpacks"><picture>
    <source media="(prefers-color-scheme: dark)" srcset="resources/serverpackcreator-dark.png" />
    <source media="(prefers-color-scheme: light)" srcset="resources/serverpackcreator-light.png" />
    <img src="resources/serverpackcreator-light.png" alt="ServerPackCreator logo" height="64" />
</picture></a>
</p>

### IDE

The plugin is built using [IntelliJ IDEA][intellij-idea], special thanks to [JetBrains][jetbrains] for this amazing
IDE, and their support to this project.

<a href="https://www.jetbrains.com/" title="JetBrains"><img src="resources/jetbrains-logo.svg" alt="JetBrains logo" height="96" /></a>
<a href="https://www.jetbrains.com/idea/" title="IntelliJ IDEA"><img src="resources/intellij-idea-icon.svg" alt="IntelliJ IDEA icon" height="96" /></a>

### Community

With their feedback, plugin improvement is possible. Special thanks to:

@[andreaschiona](https://github.com/andreaschiona),
@[apo1967](https://github.com/apo1967),
@[bric3](https://github.com/bric3),
@[byxor](https://github.com/byxor),
@[ChFlick](https://github.com/ChFlick),
@[ckosloski](https://github.com/ckosloski),
@[davidkron](https://github.com/davidkron),
@[fdw](https://github.com/fdw),
@[Griefed](https://github.com/Griefed),
@[JonasMorrissey](https://github.com/JonasMorrissey),
@[joschi](https://github.com/joschi),
@[jorgheymans](https://github.com/jorgheymans),
@[ludik0](https://github.com/ludik0),
@[marcospereira](https://github.com/marcospereira),
@[mgiorgino-iobeya](https://github.com/mgiorgino-iobeya),
@[mhalbritter](https://github.com/mhalbritter),
@[mike-howell](https://github.com/mike-howell),
@[napstr](https://github.com/napstr),
@[nitzan-n](https://github.com/nitzan-n),
@[nuth](https://github.com/nuth),
@[pmwmedia](https://github.com/pmwmedia),
@[rolaca11](https://github.com/rolaca11),
@[stephanebastian](https://github.com/stephanebastian),
@[TapaiBalazs](https://github.com/TapaiBalazs),
@[thebignet](https://github.com/thebignet)
@[tngwoerleij](https://github.com/tngwoerleij),
@[trohr](https://github.com/trohr),
@[t-h-e](https://github.com/t-h-e),
@[xehonk](https://github.com/xehonk)

[contributing]: <CONTRIBUTING.md> (Contributing to this project)
[example-fullstack-war-application]: <examples/fullstack-war-application> (Build a full-stack Spring Boot WAR application with dedicated frontend and backend subprojects)
[example-multiple-package-managers-with-shared-nodejs-distribution]: <examples/multiple-package-managers-with-shared-nodejs-distribution> (Build multiple applications with different package managers and using a shared Node.js distribution)
[example-npm-application]: <examples/npm-application> (Build a NPM application)
[example-pnpm-application]: <examples/pnpm-application> (Build a PNPM application)
[example-yarn-application-pnp-linker]: <examples/yarn-application-with-pnp-linker> (Build a YARN application with default pnp linker)
[example-yarn-application-node-modules-linker]: <examples/yarn-application-with-node-modules-linker> (Build a YARN application with node-modules linker)
[example-application-with-preinstalled-nodejs-distribution]: <examples/application-with-preinstalled-nodejs-distribution> (Build an application using a preinstalled Node.js distribution)
[examples]: <examples> (Examples)
[official-documentation]: <https://siouan.github.io/frontend-gradle-plugin/> (Official documentation of the Frontend Gradle plugin for node, npm, pnpm, yarn)
[intellij-idea]: <https://www.jetbrains.com/idea/> (IntelliJ IDEA)
[jetbrains]: <https://www.jetbrains.com/> (JetBrains)
[release-notes]: <https://github.com/siouan/frontend-gradle-plugin/releases> (Release notes)
