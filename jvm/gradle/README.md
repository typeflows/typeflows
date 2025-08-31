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

- `src/typeflows/kotlin/` - Kotlin GitHub configuration definitions
- `src/typeflows/java/` - Java GitHub configuration definitions  
- `src/typeflows/resources/` - Resource files including instructions and scripts

## What This Example Includes

- **Build Workflow** (`Build.kt`) - Demonstrates a basic CI/CD workflow
- **Deploy Workflow** (`Deploy.java`) - Shows deployment workflow patterns  
- **Custom Actions** (`RunGradleBuildAndReport.kt`) - Example of creating reusable actions
- **Resource Files** - Instructions and scripts that can be referenced in your GitHub configuration

This example shows how to replace manual `.github/` folder management with programmable, testable, and shareable code. Stop copy-pasting configurations between repositories and start treating your GitHub setup as infrastructure!

## Configuration

The Typeflows plugin is configured in `build.gradle.kts`:

```kotlin
typeflows {
    typeflowsClass = "com.example.Typeflows" // Defaults to "Typeflows"
}
```
