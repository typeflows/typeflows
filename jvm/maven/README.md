# Typeflows Maven Example

This example demonstrates how to use Typeflows with Maven to manage your entire `.github/` folder as code - workflows, security policies, branch protection, and more - using Java and Kotlin.

## Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

## Getting Started

To generate your complete `.github/` configuration from this example, run:

```bash
cd typeflows
mvn typeflows:export
```

## Project Structure

This is a multi-module Maven project:

- `typeflows/` - Contains your GitHub configuration definitions in Java and Kotlin
  - `src/main/kotlin/com/example/Typeflows.kt` - Main configuration class
  - `src/main/kotlin/com/example/actions/RunMavenBuildAndReport.kt` - Custom Maven build action
  - `src/main/kotlin/com/example/workflows/Build.kt` - Build workflow definition
  - `src/main/java/com/example/workflows/Deploy.java` - Deploy workflow (Java example)
- `app/` - Example application module
- `pom.xml` - Parent POM with module configuration

## What This Example Includes

- **Multi-module setup** - Demonstrates how to structure Typeflows in a Maven multi-module project
- **Custom Maven Actions** - RunMavenBuildAndReport action tailored for Maven projects
- **Workflow Definitions** - Build workflow with Maven-specific commands
- **Dependabot Configuration** - Automated dependency updates for Maven projects
- **Maven Plugin Integration** - Example of the Typeflows Maven plugin configuration
- **Kotlin & Java Support** - Mixed language support with proper Maven compilation

This example shows how to replace manual `.github/` folder management with programmable, testable, and shareable code. Stop copy-pasting configurations between repositories and start treating your GitHub setup as infrastructure!

## Configuration

The Typeflows and Kotlin Maven plugins are configured in `typeflows/pom.xml`.

**Important**: Since the Typeflows class is in the `com.example` package, you must specify the fully qualified class name in the plugin configuration:

```xml
<!-- Kotlin Maven Plugin -->
<plugin>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-maven-plugin</artifactId>
    <version>${kotlin.version}</version>
    <executions>
        <execution>
            <id>compile</id>
            <phase>compile</phase>
            <goals>
                <goal>compile</goal>
            </goals>
        </execution>
    </executions>
</plugin>

<!-- Typeflows Maven Plugin -->
<plugin>
    <groupId>io.typeflows</groupId>
    <artifactId>typeflows-maven-plugin</artifactId>
    <version>${typeflows.version}</version>
    <configuration>
        <targetDirectory>../</targetDirectory>
        <typeflowsClass>com.example.Typeflows</typeflowsClass>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>export</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

**Note**: The `<typeflowsClass>com.example.Typeflows</typeflowsClass>` configuration is required because the class is not in the default (root) package. If your Typeflows class is in the root package (just `Typeflows`), you can omit this configuration.

## Running the Example

1. Navigate to the maven directory: `cd jvm/maven`
2. Navigate to the typeflows module: `cd typeflows`
3. Run the export: `mvn typeflows:export`
4. Check the generated `.github/` folder in the maven directory
