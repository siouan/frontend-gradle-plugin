<h2 align="center">Frontend Gradle plugin - Integrated <a href="https://nodejs.org/" title="Node.js">Node.js</a>,
<a href="https://www.npmjs.com/" title="npm">npm</a>, <a href="https://pnpm.io/" title="pnpm">pnpm</a>,
<a href="https://yarnpkg.com/" title="Yarn">Yarn</a> builds</h2> 
<p align="center">
    <a href="https://github.com/siouan/frontend-gradle-plugin/releases/tag/v7.0.0"><img src="https://img.shields.io/badge/Latest%20release-7.0.0-blue.svg" alt="Latest release 7.0.0"/></a>
    <a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/License-Apache%202.0-green.svg" alt="License Apache 2.0"/></a>
    <br/>
    <a href="https://github.com/siouan/frontend-gradle-plugin/actions/workflows/build.yml"><img src="https://github.com/siouan/frontend-gradle-plugin/actions/workflows/build.yml/badge.svg?branch=7.0-jdk11" alt="Build status"/></a>
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
    - [Build a frontend application in a single project][example-single-project]
    - [Build a frontend application in a single project using preinstalled distributions][example-single-project-preinstalled-distributions]
    - [Build multiple frontend applications with dedicated sub-projects using shared distributions][example-multi-projects-applications]
    - [Build a full-stack Spring Boot WAR application with dedicated frontend and backend sub-projects][example-multi-projects-war-application]
- [Special thanks](#special-thanks)
- [Contributing][contributing]

## Special thanks

The plugin is built using [IntelliJ IDEA][intellij-idea], special thanks to [JetBrains][jetbrains] for this amazing
IDE, and their support to this project.

<a href="https://www.jetbrains.com/" title="JetBrains"><img src="resources/jetbrains-logo.svg" alt="JetBrains logo" height="96" /></a>
<a href="https://www.jetbrains.com/idea/" title="IntelliJ IDEA"><img src="resources/intellij-idea-icon.svg" alt="IntelliJ IDEA icon" height="96" /></a>

With their feedback, plugin improvement is possible. Special thanks to:

@[andreaschiona](https://github.com/andreaschiona),
@[apo1967](https://github.com/apo1967),
@[byxor](https://github.com/byxor),
@[ChFlick](https://github.com/ChFlick),
@[ckosloski](https://github.com/ckosloski),
@[davidkron](https://github.com/davidkron),
@[fdw](https://github.com/fdw),
@[Griefed](https://github.com/Griefed),
@[joschi](https://github.com/joschi),
@[jorgheymans](https://github.com/jorgheymans),
@[ludik0](https://github.com/ludik0),
@[mike-howell](https://github.com/mike-howell),
@[napstr](https://github.com/napstr),
@[nitzan-n](https://github.com/nitzan-n),
@[nuth](https://github.com/nuth),
@[pmwmedia](https://github.com/pmwmedia),
@[rolaca11](https://github.com/rolaca11),
@[stephanebastian](https://github.com/stephanebastian),
@[TapaiBalazs](https://github.com/TapaiBalazs),
@[tngwoerleij](https://github.com/tngwoerleij),
@[trohr](https://github.com/trohr),
@[xehonk](https://github.com/xehonk)

[contributing]: <CONTRIBUTING.md> (Contributing to this project)
[example-multi-projects-war-application]: <examples/multi-projects-war-application> (Build a full-stack Spring Boot WAR application with dedicated frontend and backend sub-projects)
[example-multi-projects-applications]: <examples/multi-projects-applications-shared-distributions> (Build multiple frontend applications with dedicated sub-projects using shared distributions)
[example-single-project]: <examples/single-project-application> (Build a frontend application in a single project)
[example-single-project-preinstalled-distributions]: <examples/single-project-application-preinstalled-distributions> (Build a frontend application in a single project using preinstalled distributions)
[examples]: <examples> (Examples)
[official-documentation]: <https://siouan.github.io/frontend-gradle-plugin/> (Official documentation of the Frontend Gradle plugin for node, npm, pnpm, yarn)
[intellij-idea]: <https://www.jetbrains.com/idea/> (IntelliJ IDEA)
[jetbrains]: <https://www.jetbrains.com/> (JetBrains)
[release-notes]: <https://github.com/siouan/frontend-gradle-plugin/releases> (Release notes)
