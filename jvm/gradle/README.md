# JVM Gradle Example

This example demonstrates how to use Typeflows with Gradle to manage your entire `.github/` folder as code - workflows, security policies, branch protection, and more - using Kotlin and Java.

## Prerequisites

- Java 21 or higher

## Getting Started

To generate the complete `.github/` configuration from this example, run:

```bash
./gradlew typeflowsExport
```

## Project Structure

- `src/typeflows/kotlin/com/example/Typeflows.kt` - Main configuration class
- `src/typeflows/kotlin/com/example/actions/RunGradleBuildAndReport.kt` - Custom Gradle build action
- `src/typeflows/kotlin/com/example/workflows/Build.kt` - Build workflow definition
- `src/typeflows/java/com/example/workflows/Deploy.java` - Deploy workflow (Java example)
- `src/typeflows/resources/` - Resource files including instructions and scripts

## What This Example Includes

- **Custom Gradle Actions** - RunGradleBuildAndReport action tailored for Gradle projects
- **Workflow Definitions** - Build and Deploy workflows with Gradle-specific commands
- **Dependabot Configuration** - Automated dependency updates (currently configured for Maven, can be changed to Gradle)
- **Gradle Plugin Integration** - Example of the Typeflows Gradle plugin configuration
- **Kotlin & Java Support** - Mixed language support with proper Gradle compilation
- **Resource Files** - Instructions and scripts that can be referenced in your GitHub configuration

This example shows how to replace manual `.github/` folder management with programmable, testable, and shareable code. Stop copy-pasting configurations between repositories and start treating your GitHub setup as infrastructure!

## Configuration

The Typeflows plugin is configured in `build.gradle.kts`.

**Important**: Since the Typeflows class is in the `com.example` package, you must specify the fully qualified class name in the plugin configuration:

```kotlin
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.typeflows)
}

dependencies {
    typeflowsApi(libs.typeflows.github)
}

typeflows {
    typeflowsClass = "com.example.Typeflows" // Required for classes not in root package
}
```

**Note**: The `typeflowsClass = "com.example.Typeflows"` configuration is required because the class is not in the default (root) package. If your Typeflows class is in the root package (just `Typeflows`), you can omit this configuration.

## Running the Example

1. Navigate to the gradle directory: `cd jvm/gradle`
2. Run the export: `./gradlew typeflowsExport`
3. Check the generated `.github/` folder in the gradle directory
