# Modern Gradle Usage Guide

## Core Concepts

- Always use Kotlin DSL (`build.gradle.kts`) instead of Groovy for better IDE support and type safety
- Enable configuration cache for faster builds: `org.gradle.configuration-cache=true`
- Use build cache when possible: `org.gradle.caching=true`

## Dependency Management

- Prefer version catalogs (`libs.versions.toml`) over direct dependency declarations
- Use dependency constraints for consistent versions
- Implement strict version resolution strategy
