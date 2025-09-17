# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

### 0.23.0-beta
- **typeflows-***: Dependency updates
- **typeflows-***: Added support for Junie as an agent configuration in generated repositories.

### 0.22.0-beta
- **typeflows-***: Dependency updates
- **typeflows-***: Added support for AGENTS.md file in generated repositories.
- **typeflows-***: File content can now convert themselves into TypeflowFile instances (convenience method `toTypeflowFile`).

### 0.21.0-beta
- **typeflows-project-standards**: Fix location of gradle wrapper properties

### 0.20.0-beta
- **typeflows-***: Dependency updates
- **typeflows-project-standards**: Ship the Typeflows standards for project structure, including gradle setup, .gitignore, README.md, LICENSE, etc.

### 0.19.0-beta
- **typeflows-***: Fix up Job YAML generation to put steps at end of Job, as other fields were interfering with it.
- **typeflows-github**: Repository dispatch event use string instead of expression as input

### 0.18.0-beta
- **typeflows-***: Add support for other files in the repository, such as README.md, LICENSE, etc.

### 0.17.0-beta
- **typeflows-***: Upgrade to Kotlin 2.2.20
- **typeflows-github**: Reworked builder workflow and directory system for API consistency, and support for new formats such as reStructuredText

### 0.16.0-beta
- **typeflows-***: Diagram generation improvements

### 0.15.0-beta
- **typeflows-***: Improve diagrams generation to handle more complex workflows
- **typeflows-***: Add support for versions files, .env files and other common configuration files in generated repositories

### 0.14.0-beta
- **typeflows-github-marketplace**: Add missing options for various action steps
- **typeflows-gradle**: Improve LLM Typeflows Import command to handle more edge cases
- **typeflows-***: Improve diagrams generation to handle more complex workflows

### 0.13.0-beta
- **typeflows-gradle**: Completely rewritten LLM Typeflows Import command
- **typeflows-github**: Implement permissions for Job
- **typeflows-github**: Refactor to remove duplicate class names. Repackaging.
- **typeflows-github-marketplace**: Repackage all applications

### 0.12.0-beta
- **typeflows-bom**: [New module] Added BOM (Bill of Materials) to manage versions of Typeflows modules.
- **typeflows-github-marketplace**: [New module] Split out marketplace actions into separate module.
- **typeflows-gradle**: Improved LLM doctor and import commands

### 0.11.0-beta
- **typeflows-***: Include .GitIgnore, .GitAttributes and .EditorConfig files in all generated repositories.
- **typeflows-github**: Support for more Marketplace actions: Cache, Checkout, ConfigurePages, CreateRelease, DeployPages, DockerBuildPush, DownloadArtifact, SetupDotNet, SetupGo, SetupGradle, SetupJava, SetupNode, SetupPython, SetupRuby, UploadArtifact

### 0.10.0-beta
- **typeflows-github**: Added first version of visualisations for Workflows and their triggers.

### 0.9.0-beta
- **typeflows-gradle**: Improved LLM doctor and import commands
- **typeflows-maven**: Improved LLM doctor and import commands

### 0.8.0-beta
- **typeflows-core**: Ability to write arbitrary directory structures as well as just files.
- **typeflows-maven**: [New module] Maven plugin for Typeflows.

### 0.7.0-beta
- **typeflows-github**: Modelling conditionals in workflows. Support for composable `if` expressions on jobs and steps.
- **typeflows-core**: Extensible architecture supports other repo platforms.
- **typeflows-gradle**: Renamed (was `typeflows-github-gradle`) Decoupled from GitHub, so can handle any Typeflows repository system.

### 0.6.0-beta
- **typeflows-github**: Introduce custom action calling steps for SetupJava and friends. Refactoring of API types for consistency.

### 0.5.0-beta
- **typeflows-github**: Simplifying Builder syntax to make consistent across all GitHub entities. 

### 0.4.0-beta
- **typeflows-github**: Reworked locations of generated GitHub files.

### 0.3.0-beta
- **typeflows-github**: Introduce DotGitHub DSL, so we can now model everything in `.github` directory (workflows, actions, depdendabot, copilot etc).

### 0.2.0-beta
- **typeflows-github-gradle**: Added install Typeflows prompts for Cursor, Claude, Copilot.

### 0.1.0-beta
- **typeflows-github**: Typeflows GitHub DSL
- **typeflows-github-gradle**: Gradle plugin for Typeflows GitHub DSL. Exports workflows/actions to `.github` directory.
