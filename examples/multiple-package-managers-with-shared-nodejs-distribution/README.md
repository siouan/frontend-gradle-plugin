## Example: build multiple frontend applications with different package managers and a shared [Node.js][nodejs]
distribution.

This example demonstrates how a single [Node.js][nodejs] distribution may be installed and shared among multiple
subprojects, and how different package managers may be used in these subprojects.

### Requirements

- A [Gradle Wrapper][gradle-wrapper] configured in this directory.

### Description

The root project defines an extra property `nodeInstallDirectory` pointing to a directory where the Node.js distribution
shall be installed. The `node-subproject` subproject downloads and installs the distribution in this directory. Other
subprojects simply reuse this property and the `nodeDistributionProvided` plugin property to avoid downloading again the
distribution. All package managers used in `package.json` files are different. Thanks to [Corepack][corepack], the
[Node.js][nodejs] distribution deals with the download of each package manager and its activation in each subproject.

Each subproject defines a custom task `<packageManager>Version` that prints the version of the package manager enabled
in the subproject. Finally, run `gradlew build` on a command line, and verify these tasks prints the expected version
based on the [`packageManager`][package-manager-field] field located in the `package.json` file.

[corepack]: <https://nodejs.org/api/corepack.html> (Corepack)
[gradle-wrapper]: <https://docs.gradle.org/current/userguide/gradle_wrapper.html> (Gradle Wrapper)
[nodejs]: <https://nodejs.org/> (Node.js)
[package-manager-field]: <https://nodejs.org/api/packages.html#packagemanager> (packageManager field)
