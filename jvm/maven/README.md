# JVM Maven Example

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
  - `src/main/java/Typeflows.java` - Java configuration class
  - `src/main/kotlin/TypeflowsKotlin.kt` - Kotlin configuration class
- `app/` - Example application module
- `pom.xml` - Parent POM with module configuration

## What This Example Includes

- **Multi-module setup** - Demonstrates how to structure Typeflows in a Maven multi-module project
- **Java & Kotlin Configuration** - Shows GitHub configuration using both Java and Kotlin
- **Maven Plugin Integration** - Example of the Typeflows Maven plugin configuration
- **Kotlin Maven Plugin** - Configured for mixed Java/Kotlin compilation
- **Separation of Concerns** - Keeps GitHub configuration separate from application code

This example shows how to replace manual `.github/` folder management with programmable, testable, and shareable code. Stop copy-pasting configurations between repositories and start treating your GitHub setup as infrastructure!

## Configuration

The Typeflows and Kotlin Maven plugins are configured in `typeflows/pom.xml`:

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
    <artifactId>typeflows-maven</artifactId>
    <version>${typeflows.version}</version>
    <configuration>
        <targetDirectory>../</targetDirectory>
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

## Running the Example

1. Navigate to the maven directory: `cd jvm/maven`
2. Navigate to the typeflows module: `cd typeflows`
3. Run the export: `mvn typeflows:export`
4. Check the generated `.github/` folder in the maven directory

## License

This example is licensed under the same terms as the main Typeflows project. See [https://typeflows.io/license](https://typeflows.io/license) for details.