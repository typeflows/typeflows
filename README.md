# Typeflows Examples

This repository contains examples demonstrating how to use the Typeflows libraries to create type-safe workflow definitions.

## Overview

Typeflows is a set of libraries that allow you to define workflows using type-safe code instead of YAML or other configuration formats. This examples repository showcases various patterns and use cases.

## Supported Platforms

- **JVM** - Kotlin/Java examples for creating GitHub Actions workflows

## Getting Started

### Prerequisites

- Java 21 or higher

### Running Examples

To generate workflows from the examples into .github/workflows, run the following command from the root of the project:

```bash
./gradlew typeflowsExport
```

## License

This project is licensed under the same terms as the main Typeflows project. See [https://typeflows.io/license](https://typeflows.io/license) for details.
