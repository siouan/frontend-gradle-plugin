# Contributing notes

## Reporting an issue

After ensuring the problem was not reported already, any issue may be created using [this page][issues].

## Development guidelines

### Starting point

- The `master` branch is a snapshot of the latest stable release. It is not intended to receive any commit/pull request
directly.
- A dedicated branch must always be created for any modification, starting from the relevant `X.Y` branch.
- A dedicated branch shall be named `feature/<my-feature>` or `fix/<my-issue>`.

### Modifying source code

- Apart from your preferred IDE, no other tools is required.
- Use the integrated Gradle Wrapper executable `gradlew` to execute development tasks apart from an IDE.
- It is an objective to keep the plugin independent, small. No dependencies shall be added without approbation.
- All packages, classes, methods shall have a relevant documentation. A relevant documentation provides all information
to identify the responsibility and behaviour of the class/method, such as developers don't have to inspect the code to
understand how to use it.
- Prefer adding relevant documentation directly in the code instead of creating an implementation document, to guarantee
accessibility for developers.
- Designing automated unit tests with a good coverage for classes tightly coupled with Gradle API is not a
trivial task, due to the design of this API. That's why all business processes in the plugin shall remain the most
independent possible, away from this API, with an appropriate level of abstraction.
- This separation between classes tightly coupled with Gradle API and classes containing business processes is
implemented with 2 packages: `org.siouan.frontendgradleplugin.tasks` and `org.siouan.frontendgradleplugin.core`. 
- Unit tests shall be written for all classes. Code coverage with unit tests shall be the highest possible, to avoid
unknown behaviours at execution, and improve software maintainability, predictability, and reliability.
- Functional tests shall be written for each task class. 

### Executing functional tests from an IDE

When working with an IDE, it may be useful to run tests regularly. The IDE and Gradle may have different compilation
output paths. When executing functional tests (located in the `org.siouan.frontendgradleplugin.tasks` package),
concrete Gradle builds are executed, and are based on Gradle's compilation output path. This path contains classes
compiled during a Gradle build, not an IDE build. Therefore, to avoid executing tests on obsolete code, it is mandatory
to execute the Gradle task `pluginUnderTestMetata` before running functional tests.

```sh
gradlew pluginUnderTestMetadata

```

### Committing

- Ideally, a feature/fix shall be provided in a single commit. Use of commit amend may be useful.
- If a commit fixes an issue, either a feature, a bug or a documentation task, provide the message
'Fixed #<issue_number>'.
- The automated test suite must be run and no test case must fail before any commit.

[issues]: <https://github.com/Siouan/frontend-gradle-plugin/issues> (Issues)
