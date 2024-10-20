# Contributing notes

## Reporting an issue

After ensuring the problem was not reported already, any issue may be created using [this page][issues].

## Development guidelines

### Git repository

#### Long-term branches

- Production branch: the `main` branch contains stable releases compiled with the most recent LTS JDK. The `HEAD`
revision matches generally the latest stable release, though it may sometimes contain above the latest release some
fixes unrelated to the packaged software (online documentation, CI workflows, etc.) It MUST receive commits only with
pull requests merged from version branches.
- Version branches: branches `<X>.<Y>[-jdk<Z>]` contain history of each version `X.Y`, eventually compiled with a
certain version of the JDK.

#### Short-term branches

These branches are development branches. They are intended to reveive development of features, bug fixes, or updates of
the build process or documentation. A development branch SHALL be named depending on its content:

- Feature: `feature/<issue-number>-<description>`
- Bug fix: `fix/<issue-number>-<description>`
- Other tasks: `task/[<issue-number>-]<description>`

The `<issue-number>` links the branch to a GitHub issue created previously in the project. Therefore, development of a
feature or bug fix MUST be preceeded by the creation of an issue describing the expected behavior. An issue MAY be
created for a development branch related to a task, since modifications frequently don't touch software files packaged
together.

#### Commit

- Development branches MAY contain one or more commits to achieve the goal.
- A pull request MUST be created to merge commits in a development branch into a version branch.
- Linear history is expected in long-term branches: merge commits are forbidden.
- A single commit by feature/fix/task is expected in long-term branches: commits in a development branch MUST be
squashed before being merged in a long-term branch. If not done by the developer, squashing is forced when the pull
request is merged.
- Commit messages MUST follow the [Conventional commits][conventional-commits] specification.
- If a commit deals with an issue, the commit message SHALL contain `fixed #<issue-number>`.

### About the source code

#### Design

The plugin relies on the following principles:

- Clean architecture: business code is entirely isolated and independent from any framework/library except the JDK.
The 2 major concepts in this architecture are use cases and providers: use cases are single-responsibility services,
while providers are interfaces with the outer world (infrastructure).
- Inversion of control: using dependency injection increases modularity, and eases unit testing.
- Unit tests: a 100% code coverage is expected for each component in the domain layer for a maximal predictability. For
components in the infrastructure layer, a maximal code coverage is expected as far as possible.
- Functional tests: allow to verify integration with Gradle (I/O validation, task dependencies, exit statuses, ...),
which cannot be done with unit tests due to the hard coupling with Gradle components 
- Code conventions: natural language is a requirement during implementation. Authors privilege long understandable
names for classes, parameters, variables, to short fuzzy ones.
- Immutability: model classes are immutable to prevent side-effects.

#### Code layout

| Package name                                                | Description                                                                                                                                              |
|:------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------|
| `org.siouan.frontendgradleplugin`                           | Root package containing the bootstrap class.                                                                                                             |
| `org.siouan.frontendgradleplugin.domain`                    | Root package of the domain/business layer.                                                                                                               |
| `org.siouan.frontendgradleplugin.domain.installer`          | Package containing classes used to download and deploy a Node.js distribution in a target directory.                                                     |
| `org.siouan.frontendgradleplugin.domain.installer.archiver` | Package containing classes used to explode Node.js packages in multiple formats.                                                                         |
| `org.siouan.frontendgradleplugin.infrastructure`            | Root package of the infrastructure layer.                                                                                                                |
| `org.siouan.frontendgradleplugin.infrastructure.archiver`   | Package containing concrete archiver implementations based on the [Apache Commons Compress][apache-commons-compress] for ZIP/GZIP/TAR extraction.        |
| `org.siouan.frontendgradleplugin.infrastructure.bean`       | Package containing a simple implementation of an IoC container.                                                                                          |
| `org.siouan.frontendgradleplugin.infrastructure.gradle`     | Package containing Gradle tasks and adapters with the domain layer.                                                                                      |
| `org.siouan.frontendgradleplugin.infrastructure.httpclient` | Package containing an implementation of a HTTP client compatible with the domain layer and based on the [Apache HttpComponents][apache-http-components]. |
| `org.siouan.frontendgradleplugin.infrastructure.system`     | Package containing implementations of adapters between the underlying system and the domain layer (file system, environment, etc.).                      |

#### Coding tips

- Apart from your preferred IDE, no other tools is required.
- Major IDEs such as [IntelliJ IDEA][intellij-idea] offer a full-featured workspace avoiding the use of the command
line. However, if an IDE cannot be used for development, the integrated Gradle Wrapper executable `gradlew` is
recommended to execute development tasks.
- It is a requirement to keep the plugin independent, small. That's why relying on 3rd-party libraries is limited as
much as possible.
- All packages, classes, methods SHOULD have a relevant documentation. A relevant documentation provides information to
identify the responsibility and behavior of the class/method, such as developers don't have to inspect the code to
understand how to use it.
- Prefer adding relevant documentation directly in the code instead of creating an implementation document, to guarantee
accessibility for all developers.

### Local development

#### Running functional tests from an IDE

Plugin metadata for user test must be generated/up-to-date to run functional tests. This is done by running Gradle task
`pluginUnderTestMetata` within the IDE (when tests execution are handled by the IDE itself instead of Gradle).

```sh
gradlew pluginUnderTestMetadata
```

#### Running the website dev server

- Open a command line in the `site` directory, and install project dependencies: `../gradlew :site:installFrontend`
- Add the `<root-project-dir>/site/node` directory to the `PATH` environment variable.
- Run dev server: `yarn run dev`
- Browse to [`http://locahost:10000/frontend-gradle-plugin/`](`http://locahost:10000/frontend-gradle-plugin/`).

## Continuous integration

The project relies on [GitHub Actions][github-actions] to integrate continuously every changes (pull requests) in the
repository. The configuration actually allows to build and test the plugin with Adoptium Temurin JDK 21 64 bits, on the
environments below:

- Linux Ubuntu 24.04
- Mac OS 14.5
- Windows Server 2022

Ubuntu is the reference O/S, used to analyze the source code with SonarCloud. Developments are frequently done on a
Windows 10 Professionnal workstation with Adoptium Temurin JDK 21 64 bits and [IntelliJ IDEA][intellij-idea].

[apache-commons-compress]: <https://commons.apache.org/proper/commons-compress/> (Apache Commons Compress)
[apache-http-components]: <https://hc.apache.org/> (Apache HttpComponents)
[conventional-commits]: <https://www.conventionalcommits.org/> (Conventional Commits)
[intellij-idea]: <https://www.jetbrains.com/idea/> (IntelliJ IDEA)
[issues]: <https://github.com/siouan/frontend-gradle-plugin/issues> (Issues)
[github-actions]: <https://github.com/siouan/frontend-gradle-plugin/actions> (GitHub Actions)
