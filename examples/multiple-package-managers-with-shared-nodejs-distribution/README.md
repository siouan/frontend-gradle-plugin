## Example: build multiple frontend applications with dedicated sub-projects using shared distributions

This example demonstrates how a single [Node.js][nodejs] distribution may be installed and shared among multiple
subprojects, each one using a different package manager.

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.

### Description

The root project defines an extra property `nodeInstallDirectory` pointing to a directory where the Node.js distribution
shall be installed. The `node-subproject` subproject downloads and installs the distribution in this directory. Other
subprojects simply reuse this property and the `nodeDistributionProvided` plugin property to avoid downloading again the
distribution. All package managers used in `package.json` files are different. Thanks to [Corepack][corepack], the
[Node.js][nodejs] distribution deals with the download of each package manager and its activation in each subproject.

Each subproject defines a custom task to show the version of the package manager it relies on. Finally,
run `gradlew build` on a command line, and check the output of these tasks.

[corepack]: <https://nodejs.org/api/corepack.html> (Corepack)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[nodejs]: <https://nodejs.org/> (Node.js)
