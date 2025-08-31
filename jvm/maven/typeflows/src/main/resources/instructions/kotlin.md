# Kotlin Coding Guidelines

## Core Principles

- Write pure functions without side effects
- Avoid mutable state and shared variables
- Use immutable data structures by default
- No reflection or "magic" solutions
- Leverage type system for compile-time safety

## Functional Programming

- Use data classes for immutable data structures
- Prefer List, Set, Map over mutable collections
- Utilize extension functions for behavior
- Apply map/flatMap for collection transformations
- Handle nullability with Option type
- Use sealed classes for union types

## HTTP4K Usage

- Model endpoints as pure functions of type: (Request) -> Response
- Use lenses for type-safe parameter extraction
- Apply filters as function composition
- Keep routes as pure functions
- Separate business logic from HTTP concerns
- Use contract DSL for API documentation

## Forkhandles Integration

- Handle errors with Result4k
- Use Values4k for type-safe domain objects
- Implement Time4k for temporal operations
- Chain operations with Result4k's map/flatMap
- Apply validation4k for input validation
- Keep domain types distinct with Values4k

## Code Style

- One function, one responsibility
- Maximum function length: 20 lines
- Use expression bodies for simple functions
- Apply meaningful naming conventions
- Document public APIs

## Testing

- Write pure unit tests
- Use approval testing for complex outputs
- Apply property-based testing
- Keep test cases isolated
- Mock external dependencies explicitly
- Use http4k test DSL for route testing
